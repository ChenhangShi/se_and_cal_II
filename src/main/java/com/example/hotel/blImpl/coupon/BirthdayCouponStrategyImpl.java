package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.member.MemberService;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.Member;
import com.example.hotel.vo.MemberVO;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {
    @Autowired
    private MemberService memberService;

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon){
        //是不是生日优惠
        if(coupon.getCouponType()!=1)
            return false;

        // 是不是普通会员
        MemberVO member=memberService.getMemberInfo(orderVO.getUserId());
        if(member==null || member.getType()!=1)
            return false;

        //入住日期是不是生日
        String checkInDate=orderVO.getCheckInDate().substring(5);
        String birthday=member.getBirthday().substring(5);
        if(!checkInDate.equals(birthday))
            return false;

        return true;
    }

}
