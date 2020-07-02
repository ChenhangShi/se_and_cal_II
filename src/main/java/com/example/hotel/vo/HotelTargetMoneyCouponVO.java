package com.example.hotel.vo;

/**
 * 酒店满减金额优惠 eg 满300减100
 */
public class HotelTargetMoneyCouponVO extends CouponVO {

    private Integer targetMoney;

    private Integer discountMoney;

    public Integer getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(Integer targetMoney) {
        this.targetMoney = targetMoney;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }

}
