package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.member.MemberService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.Methods;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String DELETE_MANAGER_ERROR = "删除酒店管理人员失败";
    private final static String DELETE_SALESMAN_ERROR = "删除网站营销人员失败";
    private final static String DELETE_USER_ERROR = "删除用户失败";

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    HotelService hotelService;
    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;

    @Override
    public ResponseVO addManager(UserForm userForm) {
        int managerId;
        try{
            User user = new User();
            user.setEmail(userForm.getEmail());
            // md5加密密码
            user.setPassword(Methods.md5Encryption(userForm.getPassword()));
            user.setUserType(UserType.HotelManager);
            adminMapper.addManagerAndSalesMan(user);
            managerId = accountService.getAccountByName(userForm.getEmail()).getId();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(managerId);
    }

    @Override
    public ResponseVO addSalesman(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        // md5加密密码
        user.setPassword(Methods.md5Encryption(userForm.getPassword()));
        user.setUserType(UserType.Salesman);
        try {
            adminMapper.addManagerAndSalesMan(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<User> getAllManagers() {
        return adminMapper.getAllManagers();
    }

    @Override
    public List<User> getAllSalesmen() {
        return adminMapper.getAllSalesmen();
    }

    @Override
    public ResponseVO deleteManager(Integer managerId){
        HotelVO hotelVO=hotelService.retrieveHotelByManager(managerId);
        //把酒店的管理人员id置为null
        if(hotelVO!=null)
            hotelService.setHotelManager(hotelVO.getId(),null);
        return deleteUser(managerId);
    }

    @Override
    public ResponseVO deleteSalesman(Integer salesmanId){
        return deleteUser(salesmanId);
    }

    @Override
    public ResponseVO deleteUser(Integer userId){
        try{
            //删除用户的订单
            List<Order> orders=orderService.getUserOrders(userId);
            for(Order order:orders)
                orderService.deleteOrder(order.getId());
            //删除用户的会员信息
            memberService.deleteMemberByUser(userId);
            //删除用户
            adminMapper.deleteUser(userId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETE_USER_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
}
