package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface OrderMapper {

    int addOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getUserOrders(@Param("userid") int userid);

    int annulOrder(@Param("orderid") int orderid);

    Order getOrderById(@Param("orderid") int orderid);

    int deleteOrderByHotel(@Param("hotelId") int hotelId);

    int deleteOrder(@Param("orderid") Integer orderid);

    int setOrderUnusual(@Param("orderid") int orderid);

    int executeOrder(@Param("orderid") int orderid);

    int finishOrder(@Param("orderid") int orderid);

    int updateCheckOutDate(@Param("orderid") int orderid, @Param("checkOutDate") String checkOutDate);
    int updateCheckInDate(@Param("orderid") int orderid, @Param("checkInDate") String checkInDate);
    int setAnnualDate(@Param("orderid") int orderid, @Param("annualDate") String annualDate);

    List<Order> getUnusualOrders();
}
