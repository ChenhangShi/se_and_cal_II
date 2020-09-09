package com.example.hotel.controller.coupon;

import com.alibaba.fastjson.JSON;
import com.example.hotel.HotelApplication;
import com.example.hotel.vo.BirthdayCouponVO;
import com.example.hotel.vo.CompanyCouponVO;
import com.example.hotel.vo.TargetRoomCouponVO;
import com.example.hotel.vo.TimeCouponVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class CouponControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddTimeCoupon() throws Exception{
        TimeCouponVO timeCouponVO=new TimeCouponVO();
        timeCouponVO.setName("节日优惠");
        timeCouponVO.setDescription("节日优惠");
        timeCouponVO.setType(4);
        timeCouponVO.setHotelId(-1);
        timeCouponVO.setDiscount(0.9);
        timeCouponVO.setStartTime("2020-06-01");
        timeCouponVO.setEndTime("2020-06-30");

        String json= JSON.toJSONString(timeCouponVO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/coupon/timeCoupon").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testAddTargetRoomCoupon() throws Exception{
        TargetRoomCouponVO targetRoomCouponVO=new TargetRoomCouponVO();
        targetRoomCouponVO.setName("多间优惠");
        targetRoomCouponVO.setDescription("多间优惠");
        targetRoomCouponVO.setType(2);
        targetRoomCouponVO.setHotelId(1);
        targetRoomCouponVO.setDiscountMoney(100);

        String json= JSON.toJSONString(targetRoomCouponVO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/coupon/targetRoomCoupon").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testAddBirthdayCoupon() throws Exception{
        BirthdayCouponVO birthdayCouponVO=new BirthdayCouponVO();
        birthdayCouponVO.setName("生日优惠");
        birthdayCouponVO.setDescription("生日优惠");
        birthdayCouponVO.setType(1);
        birthdayCouponVO.setHotelId(1);
        birthdayCouponVO.setDiscount(0.9);
        String json= JSON.toJSONString(birthdayCouponVO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/coupon/birthdayCoupon").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testAddCompanyCoupon() throws Exception{
        CompanyCouponVO companyCouponVO=new CompanyCouponVO();
        companyCouponVO.setName("企业优惠");
        companyCouponVO.setDescription("企业优惠");
        companyCouponVO.setType(5);
        companyCouponVO.setHotelId(1);
        companyCouponVO.setDiscount(0.9);
        String json= JSON.toJSONString(companyCouponVO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/coupon/companyCoupon").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetOrderMatchCoupons() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/coupon/orderMatchCoupons")
                .param("userId","1")
                .param("hotelId","1")
                .param("orderPrice","600")
                .param("roomNum","3")
                .param("checkIn","2021-01-01")
                .param("checkOut","2021-01-05")
                .param("create","2020-06-05T10:32:30")
                .contentType(MediaType.APPLICATION_JSON_UTF8)) //设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }

}
