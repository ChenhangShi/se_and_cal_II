package com.example.hotel.vo;

/** 三间及以上减免 */
public class TargetRoomCouponVO extends CouponVO{
    private int discountMoney;

    public int getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(int discountMoney) {
        this.discountMoney = discountMoney;
    }
}
