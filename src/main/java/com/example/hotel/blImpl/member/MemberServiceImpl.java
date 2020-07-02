package com.example.hotel.blImpl.member;

import com.example.hotel.bl.member.MemberService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.controller.member.MemberController;
import com.example.hotel.data.member.MemberMapper;
import com.example.hotel.po.Member;
import com.example.hotel.po.MemberLevel;
import com.example.hotel.po.User;
import com.example.hotel.vo.MemberLevelVO;
import com.example.hotel.vo.MemberVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    private final static String REGISTER_ERROR = "注册会员失败";
    private final static String DELETE_ERROR = "删除会员失败";
    private final static String CHANGE_ERROR = "修改等级信息失败";
    private final static String UPDATE_ERROR = "更新会员等级失败";

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private AccountService accountService;

    @Override
    public ResponseVO registerMember(MemberVO memberVO){
        try{
            Member member=new Member();
            BeanUtils.copyProperties(memberVO,member);

            // 计算会员等级
            double userCredit=accountService.getUserInfo(memberVO.getUserId()).getCredit();
            member.setLevel(calUserLevel(userCredit));

            memberMapper.registerMember(member);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(REGISTER_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public MemberVO getMemberInfo(int userId){
        Member member=memberMapper.getMemberInfo(userId);
        if (member==null)
            return null;
        MemberVO memberVO=new MemberVO();
        BeanUtils.copyProperties(member,memberVO);
        return memberVO;
    }

    @Override
    public List<MemberLevel> getLevelInfo(){
        return memberMapper.getLevelInfo();
    }

    @Override
    public ResponseVO changeLevelInfo(MemberLevelVO level){
        if(level.getLevel()==1)
            level.setCredit(0);
        MemberLevel memberLevel=new MemberLevel();
        BeanUtils.copyProperties(level,memberLevel);
        try{
            memberMapper.changeLevelInfo(memberLevel);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(CHANGE_ERROR);
        }

        /*
        // 每次修改等级条件 更新会员等级
        // 为提升性能 可以不要
        List<User> users=accountService.getAllUsers();
        for(User user:users)
            updateUserLevel(user);
         */

        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO deleteMemberByUser(int userId){
        MemberVO memberVO=getMemberInfo(userId);
        if(memberVO==null)
            return ResponseVO.buildSuccess(true);
        return deleteMember(memberVO.getId());
    }

    @Override
    public ResponseVO deleteMember(int id){
        try{
            memberMapper.deleteMember(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public int calUserLevel(double userCredit){
        List<MemberLevel> levels=getLevelInfo();
        int userLevel=1;
        for(MemberLevel level:levels){
            double requiredCredit=level.getCredit();
            if(userCredit<requiredCredit)
                break;
            userLevel=level.getLevel();
        }
        return userLevel;
    }

    @Override
    public void updateUserLevel(User user){
        int userLevel=calUserLevel(user.getCredit());
        try{
            memberMapper.updateUserLevel(user.getId(),userLevel);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(UPDATE_ERROR);
        }
    }
}
