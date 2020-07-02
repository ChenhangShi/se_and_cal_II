package com.example.hotel.controller.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/hotelTargetMoney")
    public ResponseVO addHotelTargetMoneyCoupon(@RequestBody HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {

        CouponVO couponVO = couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }
    @PostMapping("/timeCoupon")
    public ResponseVO addTimeCoupon(@RequestBody TimeCouponVO timeCouponVO){
        return couponService.addTimeCoupon(timeCouponVO);
    }
    @PostMapping("/targetRoomCoupon")
    public ResponseVO targetRoomCoupon(@RequestBody TargetRoomCouponVO targetRoomCouponVO){
        return couponService.addTargetRoomCoupon(targetRoomCouponVO);
    }
    @PostMapping("/birthdayCoupon")
    public ResponseVO addBirthdayCoupon(@RequestBody BirthdayCouponVO birthdayCouponVO){
        return couponService.addBirthdayCoupon(birthdayCouponVO);
    }
    @PostMapping("/companyCoupon")
    public ResponseVO addCompanyCoupon(@RequestBody CompanyCouponVO companyCouponVO){
        return couponService.addCompanyCoupon(companyCouponVO);
    }

    @GetMapping("/hotelAllCoupons")
    public ResponseVO getHotelAllCoupons(@RequestParam Integer hotelId) {
        return ResponseVO.buildSuccess(couponService.getHotelAllCoupon(hotelId));
    }

    @GetMapping("/orderMatchCoupons")
    public ResponseVO getOrderMatchCoupons(@RequestParam Integer userId,
                                           @RequestParam Integer hotelId,
                                           @RequestParam Double orderPrice,
                                           @RequestParam Integer roomNum,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut,
                                           @RequestParam String create) {
        OrderVO requestOrderVO = new OrderVO();
        requestOrderVO.setUserId(userId);
        requestOrderVO.setHotelId(hotelId);
        requestOrderVO.setPrice(orderPrice);
        requestOrderVO.setRoomNum(roomNum);
        // 格式YYYY-MM-DD
        requestOrderVO.setCheckInDate(checkIn);
        requestOrderVO.setCheckOutDate(checkOut);
        // 格式YYYY-MM-DDThh:mm:ss
        requestOrderVO.setCreateDate(create);
        return ResponseVO.buildSuccess(couponService.getMatchOrderCoupon(requestOrderVO));
    }


}
