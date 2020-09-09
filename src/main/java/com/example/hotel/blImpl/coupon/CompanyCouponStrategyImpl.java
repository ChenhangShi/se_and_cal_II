package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.member.MemberService;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.HotelCooperativeCompany;
import com.example.hotel.vo.MemberVO;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 企业会员酒店合作企业优惠
 */

@Service
public class CompanyCouponStrategyImpl implements CouponMatchStrategy {
    @Autowired
    private MemberService memberService;
    @Autowired
    private HotelService hotelService;

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon){
        //是不是企业优惠
        if(coupon.getCouponType()!=5)
            return false;

        // 是不是企业会员
        MemberVO member=memberService.getMemberInfo(orderVO.getUserId());
        if(member==null || member.getType()!=2)
            return false;

        // 是不是酒店合作企业
        List<HotelCooperativeCompany> hotelCooperativeCompanies=hotelService.getCooperativeCompanies(orderVO.getHotelId());
        String companyName=member.getCompanyName();
        for(HotelCooperativeCompany hotelCooperativeCompany:hotelCooperativeCompanies){
            if(companyName.equals(hotelCooperativeCompany.getCompanyName()))
                return true;
        }

        return false;
    }
}
