package com.example.hotel.bl.hotel;

import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface CommentService {
    /**
     * 获取该酒店的评论
     * @param hotelId
     * @return
     */
    List<Comment> getHotelComments(int hotelId);

    /**
     * 发表评论
     * @param commentVO
     * @return
     */
    ResponseVO postComment(CommentVO commentVO);
}
