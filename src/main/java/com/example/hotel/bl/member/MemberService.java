package com.example.hotel.bl.member;

import com.example.hotel.po.MemberLevel;
import com.example.hotel.po.User;
import com.example.hotel.vo.MemberLevelVO;
import com.example.hotel.vo.MemberVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface MemberService {
    ResponseVO registerMember(MemberVO memberVO);
    MemberVO getMemberInfo(int userId);
    List<MemberLevel> getLevelInfo();
    ResponseVO changeLevelInfo(MemberLevelVO level);
    ResponseVO deleteMemberByUser(int userId);
    ResponseVO deleteMember(int id);
    int calUserLevel(double userCredit);
    void updateUserLevel(User user);
}
