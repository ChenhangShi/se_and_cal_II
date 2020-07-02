package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.blImpl.admin.AdminServiceImpl;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody UserForm userForm) {

        return adminService.addManager(userForm);
    }
    @PostMapping("/addSalesman")
    public ResponseVO addSalesman(@RequestBody UserForm userForm) {

        return adminService.addSalesman(userForm);
    }

    @GetMapping("/getAllManagers")
    public ResponseVO getAllManagers() {
        return ResponseVO.buildSuccess(adminService.getAllManagers());
    }

    @GetMapping("/getAllSalesmen")
    public ResponseVO getAllSalesmen() {
        return ResponseVO.buildSuccess(adminService.getAllSalesmen());
    }

    @PostMapping("{managerId}/deleteManager")
    public ResponseVO deleteManager(@PathVariable Integer managerId){
        return adminService.deleteManager(managerId);
    }

    @PostMapping("{salesmanId}/deleteSalesman")
    public ResponseVO deleteSalesman(@PathVariable Integer salesmanId){
        return adminService.deleteSalesman(salesmanId);
    }

    @PostMapping("{userId}/deleteUser")
    public ResponseVO deleteUser(@PathVariable Integer userId){
        return adminService.deleteUser(userId);
    }

}
