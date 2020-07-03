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

    /**
     * 添加酒店限时优惠策略
     * @param timeCouponVO
     * @return
     */
    ResponseVO addTimeCoupon(TimeCouponVO timeCouponVO);

    /**
     * 添加酒店3间以上优惠策略
     * @param targetRoomCouponVO
     * @return
     */
    ResponseVO addTargetRoomCoupon(TargetRoomCouponVO targetRoomCouponVO);

    /**
     * 添加酒店普通会员生日优惠策略
     * @param birthdayCouponVO
     * @return
     */
    ResponseVO addBirthdayCoupon(BirthdayCouponVO birthdayCouponVO);

    /**
     * 添加酒店企业会员优惠策略
     * @param companyCouponVO
     * @return
     */
    ResponseVO addCompanyCoupon(CompanyCouponVO companyCouponVO);

    /**
     * 在删除酒店时被调用
     * @param hotelId
     * @throws ServiceException
     */
    void deleteCouponByHotel(Integer hotelId) throws ServiceException;
}
