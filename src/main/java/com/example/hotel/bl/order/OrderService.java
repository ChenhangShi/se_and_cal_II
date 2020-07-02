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

    void deleteOrderByHotel(Integer hotelId) throws ServiceException;

    ResponseVO deleteOrder(Integer orderid);

    void checkAnnualTooLate(Order order) throws Exception;

    //每天00:00:00检查，与项目启动时检查
    void checkUnusualOrders();
    void setOrderUnusual(Order order) throws ServiceException;

    OrderVO retrieveOrder(Integer orderid);

    ResponseVO executeOrder(int orderid);

    ResponseVO finishOrder(int orderid);

    static String getTodayStr(){
        LocalDate today=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(today);
    }

    static String getNowStr(){
        LocalDateTime now=LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(now);
    }

    void updateCheckOutDate(int orderid, String leaveDate) throws Exception;

    void updateCheckInDate(int orderid, String checkInDate) throws Exception;

    void setAnnualDate(int orderid, String annualDate) throws Exception;

    List<Order> getUnusualOrders();

    ResponseVO annualUnusualOrder(int orderid, String restoreMethod);

    void restoreRoom(Order order);
}
