package com.example.hotel.controller.hotel;

import com.alibaba.fastjson.JSON;
import com.example.hotel.HotelApplication;
import com.example.hotel.vo.HotelCooperativeCompanyVO;
import com.example.hotel.vo.HotelVO;
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

public class HotelControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc=MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testDeleteHotel4() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/hotel/4/deleteHotel")
                .param("hotelId","4"));
    }

    @Test
    public void testRetrieveHotel1ByManager() throws Exception{
        String responseString = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/hotel/8/retrieveHotelByManager")
                .param("managerId","8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)) //设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testSetHotelManager_1() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/hotel/1/setHotelManager")
                .param("hotelId","1")
                .param("managerId","")
        );
    }

    @Test
    public void testGetAllBizRegions() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/hotel/getAllBizRegions")
                .contentType(MediaType.APPLICATION_JSON_UTF8)) //设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }

    @Test
    public void testUpdateHotelInfo1() throws Exception{
        HotelVO hotelVO=new HotelVO();
        hotelVO.setId(1);
        hotelVO.setAddress("中心大厦");
        hotelVO.setBizRegion("浦东");
        hotelVO.setDescription("一个酒店");
        hotelVO.setHotelStar("Five");

        String json= JSON.toJSONString(hotelVO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/hotel/updateHotelInfo").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testAddCooperativeCompany() throws Exception{
        HotelCooperativeCompanyVO hotelCooperativeCompanyVO=new HotelCooperativeCompanyVO();
        hotelCooperativeCompanyVO.setHotelId(1);
        hotelCooperativeCompanyVO.setCompanyName("Apple");

        String json= JSON.toJSONString(hotelCooperativeCompanyVO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/hotel/addCooperativeCompany").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testDeleteCooperativeCompany() throws Exception{
        HotelCooperativeCompanyVO hotelCooperativeCompanyVO=new HotelCooperativeCompanyVO();
        hotelCooperativeCompanyVO.setHotelId(1);
        hotelCooperativeCompanyVO.setCompanyName("apple");

        String json= JSON.toJSONString(hotelCooperativeCompanyVO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/hotel/deleteCooperativeCompany").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    @Test
    public void testGetAllRooms() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/hotel/getAllRooms")
                .contentType(MediaType.APPLICATION_JSON_UTF8)) //设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }

    @Test
    public void testUpdateRoomInfo() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/hotel/1/updateRoomCurNum")
                .param("hotelId","1")
                .param("roomType","BigBed")
                .param("num","1")
        );
    }

    @Test
    public void testGetAllServices() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/hotel/getAllServices")
                .contentType(MediaType.APPLICATION_JSON_UTF8)) //设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }

    @Test
    public void testGetHotelServices1() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/hotel/1/getHotelServices")
                .param("hotelId","1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)) //设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }

    @Test
    public void testAddService() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/hotel/1/addService")
                .param("hotelId","1")
                .param("type","叫醒服务")
        );
    }

    @Test
    public void testDeleteService() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/hotel/1/deleteService")
                .param("hotelId","1")
                .param("type","叫醒服务")
        );
    }
}
