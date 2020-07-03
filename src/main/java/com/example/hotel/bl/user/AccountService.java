package com.example.hotel.bl.user;

import com.example.hotel.po.CreditRecord;
import com.example.hotel.po.User;
import com.example.hotel.vo.*;

import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     *
     * @return
     */
    ResponseVO registerAccount(UserVO userVO);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     *
     * @return
     */
    User login(UserForm userForm);

    /**
     * 获取用户个人信息
     * @param id
     * @return
     */
    User getUserInfo(int id);

    /**
     * 更新用户个人信息
     * @param id
     * @param password
     * @param username
     * @param phonenumber
     * @return
     */
    ResponseVO updateUserInfo(int id, String password,String username,String phonenumber);

    void updateUserCredit(int id,double credit);

    /**
     * 根据email取用户信息
     * @param email
     * @return
     */
    User getAccountByName(String email);

    List<User> getAllUsers();

    /**
     * 为用户充值信用，100*amount
     * @param userId
     * @param amount
     * @return
     */
    ResponseVO addCredit(int userId, double amount);

    /**
     * 获取用户信用变更记录
     * @param userId
     * @return
     */
    List<CreditRecord> getUserCreditRecords(int userId);
}
