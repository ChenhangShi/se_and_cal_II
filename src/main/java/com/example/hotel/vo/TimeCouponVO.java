package com.example.hotel.vo;

public class TimeCouponVO extends CouponVO {
    private String startTime; //格式yyyy-mm-dd
    private String endTime;

    //自行决定是打折还是直接减金额 如果选择打折就把targetMoney变为负数 否则就把discount变为负数

    private double discount;
    private Integer targetMoney;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Integer getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(Integer targetMoney) {
        this.targetMoney = targetMoney;
    }
}
