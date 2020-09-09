package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     * @return
     */
    List<Order> getAllOrders();

    /**
     * 获得指定用户的所有订单信息
     * @param userid
     * @return
     */
    List<Order> getUserOrders(int userid);

    /**
     * 撤销订单
     * @param orderid
     * @return
     */
    ResponseVO annulOrder(int orderid);

    /**
     * 查看酒店的所有订单
     * @param hotelId
     * @return
     */
    List<Order> getHotelOrders(Integer hotelId);

    /**
     * 删除酒店时调用
     * @param hotelId
     * @throws ServiceException
     */
    void deleteOrderByHotel(Integer hotelId) throws ServiceException;

    /**
     * 删除一条订单记录
     * @param orderid
     * @return
     */
    ResponseVO deleteOrder(Integer orderid);

    /**
     * 撤销时间是否距离最晚入住时间不满6小时
     * @param order
     * @throws Exception
     */
    void checkAnnualTooLate(Order order) throws Exception;

    /**
     * 每天00:00:00检查，与项目启动时检查，是否有订单在最晚入住时间后仍然未执行
     */
    void checkUnusualOrders();

    /**
     * 把订单置为异常
     * @param order
     * @throws ServiceException
     */
    void setOrderUnusual(Order order) throws ServiceException;

    /**
     * 取一条订单
     * @param orderid
     * @return
     */
    OrderVO retrieveOrder(Integer orderid);

    /**
     * 订单执行
     * @param orderid
     * @return
     */
    ResponseVO executeOrder(int orderid);

    /**
     * 退房
     * @param orderid
     * @return
     */
    ResponseVO finishOrder(int orderid);

    void updateCheckOutDate(int orderid, String leaveDate) throws Exception;

    void updateCheckInDate(int orderid, String checkInDate) throws Exception;

    /**
     * 把订单的创建日期置为撤销日期
     * @param orderid
     * @param annualDate
     * @throws Exception
     */
    void setAnnualDate(int orderid, String annualDate) throws Exception;

    List<Order> getUnusualOrders();

    /**
     * 撤销异常订单，恢复全部或一半信用
     * @param orderid
     * @param restoreMethod
     * @return
     */
    ResponseVO annualUnusualOrder(int orderid, String restoreMethod);

    /**
     * 归还房间
     * @param order
     */
    void restoreRoom(Order order);
}
