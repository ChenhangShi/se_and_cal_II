package com.example.hotel.controller.user;

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
public class AccountControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllUsers() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user/getAllUsers")
                .contentType(MediaType.APPLICATION_JSON_UTF8)) //设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }

    @Test
    public void testAddCredit() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/4/addCredit").param("userId","4").param("amount","1.5"));
    }

    @Test
    public void testGetUserCreditRecords5() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user/5/getUserCreditRecords")
                .param("userId","5")
                .contentType(MediaType.APPLICATION_JSON_UTF8)) //设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }
}
