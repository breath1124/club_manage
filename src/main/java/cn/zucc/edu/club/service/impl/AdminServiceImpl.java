package cn.zucc.edu.club.service.impl;

import cn.zucc.edu.club.entity.Admin;
import cn.zucc.edu.club.mapper.AdminMapper;
import cn.zucc.edu.club.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminService adminService;

    @Override
    public PageInfo<Admin> findStuByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> admins = adminService.list();
        return new PageInfo<>(admins);
    }

    @Override
    public PageInfo<Admin> findStuByPageVague(String name, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Admin> qw = new QueryWrapper<Admin>().lambda().like(Admin::getAdminAccount, name);
        List<Admin> admins = adminService.list(qw);
        return new PageInfo<>(admins);
    }

}
