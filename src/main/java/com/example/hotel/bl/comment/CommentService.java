package com.example.hotel.bl.comment;

import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface CommentService {
    List<Comment> getHotelComments(int hotelId);
    ResponseVO postComment(CommentVO commentVO);
}
