package com.example.hotel.blImpl.user;

import com.example.hotel.bl.member.MemberService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.RecordService;
import com.example.hotel.data.user.RecordMapper;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.po.User;
import com.example.hotel.util.Methods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    private final static String ADD_CREDIT_RECORD_ERROR = "添加信用记录失败";

    @Autowired
    RecordMapper recordMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    MemberService memberService;

    @Override
    // 凡是有信用变化的地方就要调用
    // 在实际操作完后调用
    public void addCreditRecord(int userId, Integer orderId, double change, String action){
        try{
            User user=accountService.getUserInfo(userId);
            // 更新用户会员等级
            memberService.updateUserLevel(user);

            CreditRecord creditRecord=new CreditRecord();
            creditRecord.setUserId(userId);
            creditRecord.setOrderId(orderId);
            creditRecord.setAction(action);
            creditRecord.setChange(change);
            creditRecord.setResult(user.getCredit());
            creditRecord.setTime(Methods.getNowStr());
            recordMapper.addCreditRecord(creditRecord);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(ADD_CREDIT_RECORD_ERROR);
        }
    }

    @Override
    public List<CreditRecord> getUserCreditRecords(int userId){
        List<CreditRecord> records = recordMapper.getUserCreditRecords(userId);
        Collections.reverse(records);
        return records;
    }

}
