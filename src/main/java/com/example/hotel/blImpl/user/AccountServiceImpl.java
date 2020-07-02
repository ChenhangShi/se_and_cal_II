package com.example.hotel.blImpl.user;

import com.example.hotel.bl.member.MemberService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.bl.user.RecordService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String UPDATE_ERROR = "修改失败";
    private final static String ADD_CREDIT_ERROR = "充值信用失败";

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RecordService recordService;
    @Autowired
    private MemberService memberService;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        try {
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public User login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getEmail());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        /*
        //登录时刷新订单状态，防止异常订单没有被置为异常，信用没有减
        orderService.getUserOrders(user.getId());
        //信用被刷新，重新获取
        user=accountMapper.getAccountById(user.getId());
         */
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public ResponseVO updateUserInfo(int id, String password, String username, String phonenumber) {
        try {
            accountMapper.updateAccount(id, password, username, phonenumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    // 加{credit}
    public void updateUserCredit(int id,double credit){
        try{
            accountMapper.updateUserCredit(id,credit);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据库更新用户信用错误");
        }
    }

    @Override
    public User getAccountByName(String email){
        return accountMapper.getAccountByName(email);
    }

    @Override
    public List<User> getAllUsers(){
        return accountMapper.getAllUsers();
    }

    @Override
    public ResponseVO addCredit(int userId, double amount){
        double credit=amount*100;
        try{
            updateUserCredit(userId, credit);
            recordService.addCreditRecord(userId,null,credit,"充值");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ADD_CREDIT_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<CreditRecord> getUserCreditRecords(int userId){
        return recordService.getUserCreditRecords(userId);
    }
}
