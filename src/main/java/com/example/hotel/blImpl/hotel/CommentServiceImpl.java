package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.CommentService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.data.hotel.CommentMapper;
import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final static String COMMENT_ERROR = "评论失败";

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    private HotelService hotelService;

    public List<Comment> getHotelComments(int hotelId){
        return commentMapper.getHotelComments(hotelId);
    }

    public ResponseVO postComment(CommentVO commentVO){
        try{
            Comment comment=new Comment();
            BeanUtils.copyProperties(commentVO,comment);
            commentMapper.postComment(comment);
            // 添加优惠后要更新酒店评分
            hotelService.updateHotelRate(commentVO.getHotelId(),commentVO.getRate());

        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(COMMENT_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }


}
