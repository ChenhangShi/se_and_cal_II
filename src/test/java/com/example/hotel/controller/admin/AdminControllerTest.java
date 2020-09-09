package com.example.hotel.controller.admin;


import com.alibaba.fastjson.JSON;
import com.example.hotel.HotelApplication;
import com.example.hotel.vo.UserForm;
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
public class AdminControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testDeleteManager8() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/8/deleteManager").param("managerId","8"));
    }

    @Test
    public void testAddManager() throws Exception{
        UserForm userForm=new UserForm();
        userForm.setEmail("2@qq.com");
        userForm.setPassword("123456");
        String json= JSON.toJSONString(userForm);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/addManager").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
        .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testAddSalesman() throws Exception{
        UserForm userForm=new UserForm();
        userForm.setEmail("3@qq.com");
        userForm.setPassword("123456");
        String json= JSON.toJSONString(userForm);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/addSalesman").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetAllSalesmen() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/admin/getAllSalesmen"))
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }

    @Test
    public void testDeleteUser5() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/5/deleteUser").param("userId","5"));
    }
}
