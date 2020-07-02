package com.example.hotel.controller.comment;

import com.alibaba.fastjson.JSON;
import com.example.hotel.HotelApplication;
import com.example.hotel.vo.CommentVO;
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
public class CommentControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetHotelComments1() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/comment/1/getHotelComments"))
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse();
    }

    @Test
    public void testPostComment() throws Exception{

        CommentVO commentVO=new CommentVO();
        commentVO.setHotelId(1);
        commentVO.setHotelName("汉庭酒店");
        commentVO.setUserId(5);
        commentVO.setUserName("测试二号");
        commentVO.setRate(2);
        commentVO.setComment("assff");

        String json= JSON.toJSONString(commentVO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/comment/postComment").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}
