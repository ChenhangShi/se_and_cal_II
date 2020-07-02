package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.CommentService;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{hotelId}/getHotelComments")
    public ResponseVO getHotelComments(@PathVariable Integer hotelId){
        return ResponseVO.buildSuccess(commentService.getHotelComments(hotelId));
    }

    @PostMapping("/postComment")
    public ResponseVO postComment(@RequestBody CommentVO commentVO){
        return commentService.postComment(commentVO);
    }
}
