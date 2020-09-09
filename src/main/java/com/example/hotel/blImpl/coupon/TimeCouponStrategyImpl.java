package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 限时优惠
 */

@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种限时优惠策略
     * @param orderVO
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        if(coupon.getCouponType()==4) {

            try {
                LocalDateTime startTime = coupon.getStartTime();
                LocalDateTime endTime = coupon.getEndTime();
                LocalDateTime date = LocalDateTime.parse(orderVO.getCreateDate());
                return startTime.isBefore(date) && endTime.isAfter(date);
            } catch (Exception e) {
                System.out.println("createDate error");
                return false;
            }
        }

/*
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime startTime = coupon.getStartTime();
            LocalDateTime endTime = coupon.getEndTime();
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                LocalDateTime date = parser.parse(orderVO.getCreateDate()).toInstant().atZone(zone).toLocalDateTime();
                return startTime.isBefore(date) && endTime.isAfter(date);
            } catch (Exception e) {
                System.out.println("createDate error");
                return false;
            }
        }
 */
        return false;
    }
}
