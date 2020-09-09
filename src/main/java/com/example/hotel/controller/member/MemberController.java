package com.example.hotel.controller.member;

import com.example.hotel.bl.member.MemberService;
import com.example.hotel.vo.MemberLevelVO;
import com.example.hotel.vo.MemberVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/registerMember")
    public ResponseVO registerMember(@RequestBody MemberVO memberVO){
        return memberService.registerMember(memberVO);
    }

    @GetMapping("/{userId}/getMemberInfo")
    public ResponseVO getMemberInfo(@PathVariable int userId){
        return ResponseVO.buildSuccess(memberService.getMemberInfo(userId));
    }

    @GetMapping("/getLevelInfo")
    public ResponseVO getLevelInfo(){
        return ResponseVO.buildSuccess(memberService.getLevelInfo());
    }

    @PostMapping("/changeLevelInfo")
    public ResponseVO changeLevelInfo(@RequestBody MemberLevelVO level){
        return memberService.changeLevelInfo(level);
    }
}
