package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class CouponServiceImpl implements CouponService {
    private final static String ADD_COUPON_ERROR="添加优惠失败";

    private final CouponMapper couponMapper;

    private final List<CouponMatchStrategy> strategyList;

    @Autowired
    public CouponServiceImpl(CouponMapper couponMapper,List<CouponMatchStrategy> strategyList) {
        //持有各种优惠策略
        this.couponMapper = couponMapper;
        this.strategyList = strategyList;
    }



    @Override
    public List<Coupon> getMatchOrderCoupon(OrderVO orderVO) {
        //获取本酒店的优惠和全局优惠
        List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());

        List<Coupon> availAbleCoupons = new ArrayList<>();

        //对于本酒店的优惠和全局优惠，检查本订单是不是符合优惠
        for (int i = 0; i < hotelCoupons.size(); i++) {
            for (CouponMatchStrategy strategy : strategyList) {
                if (strategy.isMatch(orderVO, hotelCoupons.get(i))) {
                    availAbleCoupons.add(hotelCoupons.get(i));
                }
            }
        }

        return availAbleCoupons;
    }

    @Override
    public List<Coupon> getHotelAllCoupon(Integer hotelId) {
        List<Coupon> hotelCoupons = couponMapper.selectByHotelId(hotelId);
        return hotelCoupons;
    }





    /**
     * 金额优惠的discount为-1，折扣优惠的discountMoney为-1
     */

    @Override
    public CouponVO addHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setTargetMoney(couponVO.getTargetMoney());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setStatus(1);
        coupon.setDiscount(-1);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }
    @Override
    public ResponseVO addTimeCoupon(TimeCouponVO couponVO){
        try{
            Coupon coupon=new Coupon();
            coupon.setCouponName(couponVO.getName());
            coupon.setDescription(couponVO.getDescription());
            coupon.setCouponType(couponVO.getType());
            coupon.setHotelId(couponVO.getHotelId());
            coupon.setStatus(1);
            coupon.setDiscount(couponVO.getDiscount());
            coupon.setDiscountMoney(-1);

            //开始时间为00:00:00，结束时间为23:59:59
            String start=couponVO.getStartTime();
            String end=couponVO.getEndTime();
            LocalDate startDate=LocalDate.parse(start);
            LocalDate endDate=LocalDate.parse(end);
            LocalDateTime startTime=LocalDateTime.of(startDate, LocalTime.of(0,0,0));
            LocalDateTime endTime=LocalDateTime.of(endDate,LocalTime.of(23,59,59));
            coupon.setStartTime(startTime);
            coupon.setEndTime(endTime);

            couponMapper.insertCoupon(coupon);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ADD_COUPON_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public ResponseVO addTargetRoomCoupon(TargetRoomCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setStatus(1);
        coupon.setDiscount(-1);
        try{
            couponMapper.insertCoupon(coupon);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ADD_COUPON_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public ResponseVO addBirthdayCoupon(BirthdayCouponVO couponVO){
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setDiscountMoney(-1);
        coupon.setStatus(1);
        try{
            couponMapper.insertCoupon(coupon);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ADD_COUPON_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public ResponseVO addCompanyCoupon(CompanyCouponVO couponVO){
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setDiscountMoney(-1);
        coupon.setStatus(1);
        try{
            couponMapper.insertCoupon(coupon);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ADD_COUPON_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void deleteCouponByHotel(Integer hotelId) throws ServiceException{
        couponMapper.deleteCouponByHotel(hotelId);
    }
}
