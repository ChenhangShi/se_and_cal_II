package com.example.hotel.controller.member;

import com.alibaba.fastjson.JSON;
import com.example.hotel.HotelApplication;
import com.example.hotel.vo.MemberLevelVO;
import com.example.hotel.vo.MemberVO;
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
public class MemberControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testRegisterMember() throws Exception{
        MemberVO memberVO=new MemberVO();
        memberVO.setUserId(1);
        memberVO.setType(1);
        memberVO.setBirthday("2020-01-01");

        String json= JSON.toJSONString(memberVO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/registerMember").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    @Test
    public void testGetMemberInfo1() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/member/1/getMemberInfo"))
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }

    @Test
    public void testGetLevelInfo() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/member/getLevelInfo"))
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }

    @Test
    public void testChangeLevelInfo() throws Exception{
        MemberLevelVO memberLevelVO=new MemberLevelVO();
        memberLevelVO.setLevel(2);
        memberLevelVO.setCredit(100);
        memberLevelVO.setDiscount(0.8);

        String json= JSON.toJSONString(memberLevelVO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/changeLevelInfo").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));

    }
}
