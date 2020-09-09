package com.example.hotel.controller.order;

import com.example.hotel.HotelApplication;
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

public class OrderControllerTest {



    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testDeleteOrder1() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/1/deleteOrder").param("orderid","1"));
    }

    @Test
    public void testRetrieveOrder14() throws Exception{
        String responseString = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/order/14/retrieveOrder")
                .param("orderid","14")
                .contentType(MediaType.APPLICATION_JSON_UTF8)) //设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testExecuteOrder19() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/19/executeOrder").param("orderid","19"));
    }

    @Test
    public void testFinishOrder19() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/19/finishOrder").param("orderid","19"));
    }

    @Test
    public void testGetUnusualOrders() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/order/getUnusualOrders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)) //设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }

    @Test
    public void testAnnualUnusualOrder16() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/16/annualUnusualOrder").param("orderid","16").param("restoreMethod","half"));
    }
}

