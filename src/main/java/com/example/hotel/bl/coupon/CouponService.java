package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;

import java.util.List;

public interface CouponService {
    /**
     * 返回某一订单可用的优惠策略列表
     * @param orderVO
     * @return
     */
    List<Coupon> getMatchOrderCoupon(OrderVO orderVO);

    /**
     * 查看某个酒店提供的所有优惠策略（包括失效的）
     * @param hotelId
     * @return
     */
    List<Coupon> getHotelAllCoupon(Integer hotelId);

    /**
     * 添加酒店满减优惠策略
     * @param couponVO
     * @return
     */
    CouponVO addHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO couponVO);
    ResponseVO addTimeCoupon(TimeCouponVO timeCouponVO);
    ResponseVO addTargetRoomCoupon(TargetRoomCouponVO targetRoomCouponVO);
    ResponseVO addBirthdayCoupon(BirthdayCouponVO birthdayCouponVO);
    ResponseVO addCompanyCoupon(CompanyCouponVO companyCouponVO);

    void deleteCouponByHotel(Integer hotelId) throws ServiceException;
}
