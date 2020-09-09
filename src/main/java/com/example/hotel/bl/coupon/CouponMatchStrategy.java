package com.example.hotel.bl.coupon;

import com.example.hotel.po.Coupon;

import com.example.hotel.vo.OrderVO;

public interface CouponMatchStrategy {

    /**
     * 判断该订单是否符合该优惠
     * @param orderVO
     * @param coupon
     * @return
     */
    boolean isMatch(OrderVO orderVO, Coupon coupon);

}
