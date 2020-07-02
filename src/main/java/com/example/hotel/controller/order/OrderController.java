package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }

    @GetMapping("/{orderid}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderid){
        return orderService.annulOrder(orderid);
    }

    @GetMapping("/{hotelId}/allOrders")
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }

    @PostMapping("/{orderid}/deleteOrder")
    public ResponseVO deleteOrder(@PathVariable Integer orderid){
        return orderService.deleteOrder(orderid);
    }

    //多余的，前端查看订单详情不用重新取
    @GetMapping("/{orderid}/retrieveOrder")
    public ResponseVO retrieveOrder(@PathVariable Integer orderid){
        return ResponseVO.buildSuccess(orderService.retrieveOrder(orderid));
    }

    @PostMapping("/{orderid}/executeOrder")
    public ResponseVO executeOrder(@PathVariable int orderid){
        return orderService.executeOrder(orderid);
    }
    @PostMapping("/{orderid}/finishOrder")
    public ResponseVO finishOrder(@PathVariable int orderid){
        return orderService.finishOrder(orderid);
    }

    @GetMapping("/getUnusualOrders")
    public ResponseVO getUnusualOrders(){
        return ResponseVO.buildSuccess(orderService.getUnusualOrders());
    }

    @PostMapping("/{orderid}/annualUnusualOrder")
    public ResponseVO annualUnusualOrder(@PathVariable int orderid, @RequestParam String restoreMethod){
        return orderService.annualUnusualOrder(orderid,restoreMethod);
    }
}
