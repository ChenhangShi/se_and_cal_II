package com.example.hotel.data.member;

import com.example.hotel.po.Member;
import com.example.hotel.po.MemberLevel;
import com.example.hotel.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberMapper {
    int registerMember(Member member);
    Member getMemberInfo(int userId);
    List<MemberLevel> getLevelInfo();
    int changeLevelInfo(MemberLevel level);
    int deleteMember(int id);
    int updateUserLevel(int userId, int level);
}
