package com.example.hotel.bl.member;

import com.example.hotel.po.MemberLevel;
import com.example.hotel.po.User;
import com.example.hotel.vo.MemberLevelVO;
import com.example.hotel.vo.MemberVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface MemberService {

    ResponseVO registerMember(MemberVO memberVO);

    /**
     * 获取该用户的会员信息
     * @param userId
     * @return
     */
    MemberVO getMemberInfo(int userId);

    /**
     * 获取会员等级信息，包括达到某等级需要的信用和折扣
     * @return
     */
    List<MemberLevel> getLevelInfo();

    /**
     * 修改会员等级信息，包括达到某等级需要的信用和折扣，1级需要的信用强制为0
     * @param level
     * @return
     */
    ResponseVO changeLevelInfo(MemberLevelVO level);

    /**
     * 删除用户时调用
     * @param userId
     * @return
     */
    ResponseVO deleteMemberByUser(int userId);

    /**
     * 删除一条会员记录
     * @param id
     * @return
     */
    ResponseVO deleteMember(int id);

    /**
     * 根据用户的信用值计算等级
     * @param userCredit
     * @return
     */
    int calUserLevel(double userCredit);

    /**
     * 更新用户信用
     * @param user
     */
    void updateUserLevel(User user);
}
