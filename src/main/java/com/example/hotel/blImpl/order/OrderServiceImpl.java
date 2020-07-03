package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.RecordService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.Methods;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOM_NUM_LACK = "预订房间数量剩余不足";
    private final static String ANNUAL_ERROR = "取消失败";
    private final static String DELETE_ERROR = "删除订单失败";
    private final static String EXECUTE_ERROR = "执行订单失败";
    private final static String FINISH_ERROR = "结束订单失败";
    private final static String ANNUAL_UNUSUAL_ERROR = "撤销异常订单失败";
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;
    @Autowired
    RecordService recordService;

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType());
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOM_NUM_LACK);
        }
        try {
            //设置创建日期
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);

            orderVO.setOrderState("已预订");
            User user = accountService.getUserInfo(orderVO.getUserId());
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
       return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> allOrders = orderMapper.getAllOrders();
        Collections.reverse(allOrders);
//        checkUnusualOrders(allOrders);
        return allOrders;
    }

    @Override
    public List<Order> getUserOrders(int userid) {
        List<Order> userOrders = orderMapper.getUserOrders(userid);
        Collections.reverse(userOrders);
//        checkUnusualOrders(userOrders);
        return userOrders;
    }

    @Override
    public void restoreRoom(Order order){
        int roomNums=-order.getRoomNum();
        hotelService.updateRoomInfo(order.getHotelId(),order.getRoomType(),roomNums);
    }

    @Override
    public ResponseVO annulOrder(int orderid) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        try{
            Order order=orderMapper.getOrderById(orderid);
            // 检查是否撤销太晚
            checkAnnualTooLate(order);
            restoreRoom(order);
            orderMapper.annulOrder(orderid);
            setAnnualDate(orderid, Methods.getTodayStr());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ANNUAL_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    /**
     * @param hotelId
     * @return
     */
    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = getAllOrders();
//        checkUnusualOrders(orders);
        List<Order> hotelOrders = orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
        return hotelOrders;
    }

    @Override
    public void deleteOrderByHotel(Integer hotelId) throws ServiceException{
        orderMapper.deleteOrderByHotel(hotelId);
    }

    @Override
    public ResponseVO deleteOrder(Integer orderid){
        try{
            Order order=orderMapper.getOrderById(orderid);
            // 如果房间未归还 归还房间
            if(order.getOrderState().equals("已预订") || order.getOrderState().equals("异常")){
                restoreRoom(order);
            }
            orderMapper.deleteOrder(orderid);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void checkAnnualTooLate(Order order) throws Exception{
        LocalDateTime annualTime=LocalDateTime.now();
        String checkInDate=order.getCheckInDate();
        LocalDate d=LocalDate.parse(checkInDate);
        LocalTime t=LocalTime.of(23,59,59);
        LocalDateTime latestCheckInTime=LocalDateTime.of(d,t);
        // 如果撤销时间距离最晚入住时间不满6小时
        if(annualTime.plusHours(6).isAfter(latestCheckInTime)){
            // 扣除此订单一半价格的信用
            double change=-Math.abs(order.getPrice())/2;
            accountService.updateUserCredit(order.getUserId(),change);

            recordService.addCreditRecord(order.getUserId(), order.getId(),change,"订单撤销");
        }
    }

    //项目每次启动，与每天00:00:00，扫描异常订单
    @Override
    @Scheduled(cron="0 0 0 * * *")
    @PostConstruct
    public void checkUnusualOrders(){
        List<Order> orderList=getAllOrders();
        LocalDate localDate=LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(Order order:orderList){
            try{
                LocalDate checkInDate=LocalDate.parse(order.getCheckInDate());
                // 订单在最晚入住时间后仍然未执行
                if(order.getOrderState().equals("已预订") && checkInDate.isBefore(localDate)){
                    setOrderUnusual(order);
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("解析入住时间错误或数据库错误");
            }
        }
    }
    @Override
    public void setOrderUnusual(Order order) throws ServiceException{
        order.setOrderState("异常");
        int orderid=order.getId();
        int userId=order.getUserId();
        // 扣除此订单价格的信用
        double change=-order.getPrice();
        accountService.updateUserCredit(userId,change);
        orderMapper.setOrderUnusual(orderid);

        recordService.addCreditRecord(userId,orderid,change,"订单异常");
    }

    @Override
    public OrderVO retrieveOrder(Integer orderid){
        Order order=orderMapper.getOrderById(orderid);
        /*
        List<Order> temp=new ArrayList<>();
        temp.add(order);
        checkUnusualOrders(temp);

         */
        OrderVO orderVO=new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        return orderVO;
    }

    @Override
    public ResponseVO executeOrder(int orderid){
        try{
            Order order=orderMapper.getOrderById(orderid);
            // 增加此订单价格的信用
            double change=order.getPrice();
            accountService.updateUserCredit(order.getUserId(),change);

            orderMapper.executeOrder(orderid);
            updateCheckInDate(orderid,Methods.getTodayStr());
            recordService.addCreditRecord(order.getUserId(),orderid,change,"订单执行");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(EXECUTE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO finishOrder(int orderid){
        try{
            Order order=orderMapper.getOrderById(orderid);
            // 归还房间
            restoreRoom(order);

            orderMapper.finishOrder(orderid);
            updateCheckOutDate(orderid,Methods.getTodayStr());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(FINISH_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void updateCheckOutDate(int orderid, String leaveDate) throws Exception{
        orderMapper.updateCheckOutDate(orderid,leaveDate);
    }

    @Override
    public void updateCheckInDate(int orderid, String checkInDate) throws Exception{
        orderMapper.updateCheckInDate(orderid,checkInDate);
    }

    @Override
    public void setAnnualDate(int orderid, String annualDate) throws Exception{
        orderMapper.setAnnualDate(orderid,annualDate);
    }


    @Override
    public List<Order> getUnusualOrders(){
        List<Order> orders = orderMapper.getUnusualOrders();
        Collections.reverse(orders);
        return orders;
    }

    @Override
    public ResponseVO annualUnusualOrder(int orderid, String restoreMethod){
        try{
            Order order=orderMapper.getOrderById(orderid);
            restoreRoom(order);
            orderMapper.annulOrder(orderid);
            setAnnualDate(orderid,Methods.getTodayStr());

            double change=0;
            if(restoreMethod.equals("full"))
                change=order.getPrice();
            else if(restoreMethod.equals("half"))
                change=order.getPrice()/2;
            accountService.updateUserCredit(order.getUserId(),change);

            recordService.addCreditRecord(order.getUserId(),orderid,change,"撤销异常订单");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ANNUAL_UNUSUAL_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
}
