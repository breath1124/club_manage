package cn.zucc.edu.club.controller;


import cn.zucc.edu.club.entity.Admin;
import cn.zucc.edu.club.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
@Api(tags = "管理员接口")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "注册管理员")
    @PostMapping("/add")
    public boolean addAdmin(@RequestBody Admin admin) {
        LambdaQueryWrapper<Admin> qw = new QueryWrapper<Admin>().lambda().eq(Admin::getAdminAccount, admin.getAdminAccount());
        if(adminService.list(qw).isEmpty())
            return adminService.save(admin);
        else
            throw new RuntimeException();
    }

    @ApiOperation(value = "删除管理员")
    @PostMapping("/delete")
    public boolean delAdmin(@RequestParam("adminId") int id) {
        return adminService.removeById(id);
    }

    @ApiOperation(value = "修改管理员")
    @PostMapping("/modify")
    public boolean modifyAdmin(@RequestBody Admin admin) {
        return adminService.saveOrUpdate(admin);
    }

    @ApiOperation(value = "列出所有管理员")
    @GetMapping("/listAll")
    public List<Admin> searchAllAdmin() {
        List<Admin> admins = adminService.list();
        return admins;
    }


    @ApiOperation(value = "模糊查询管理员")
    @GetMapping("/listVague")
    public List<Admin> searchVagueAdmin(@RequestParam("adminAccount") String account) {
        LambdaQueryWrapper<Admin> qw = new QueryWrapper<Admin>().lambda().like(Admin::getAdminAccount, account);
        return adminService.list(qw);
    }

    @ApiOperation(value = "管理员登录")
    @GetMapping("/login")
    public Admin loginAdmin(@RequestParam("adminAccount") String account, @RequestParam("adminPwd") String pwd) {
        LambdaQueryWrapper<Admin> qw = new QueryWrapper<Admin>().lambda().eq(Admin::getAdminAccount, account);
        Admin admin = adminService.getOne(qw);
        if(pwd.equals(admin.getAdminPwd()))
            return admin;
        else
            return null;
    }
}

