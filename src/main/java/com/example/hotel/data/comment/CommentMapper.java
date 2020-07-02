package com.example.hotel.data.comment;

import com.example.hotel.po.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    List<Comment> getHotelComments(int hotelId);
    int postComment(Comment comment);
}
