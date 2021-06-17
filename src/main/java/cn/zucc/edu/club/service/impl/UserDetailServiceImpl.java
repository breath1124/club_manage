package cn.zucc.edu.club.service.impl;

import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.security.AccountUser;
import cn.zucc.edu.club.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private StudentService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //数据库中获取用户信息
        Student sysUser = sysUserService.getByStuNum(username);

        if (sysUser == null)
            throw new UsernameNotFoundException("用户名或密码不正确");

        //参数: 用户id 用户名 用户密码 权限信息
        return new AccountUser(sysUser.getStuId(),sysUser.getStuNum(),sysUser.getStuPwd(), sysUser.getStuName(),
                sysUser.getStuAge(), sysUser.getStuTel(), sysUser.getStuIsPresident(), sysUser.getStuState(),
                sysUser.getRole(), null);
//        return new AccountUser(sysUser.getStuId(),sysUser.getUsername(),sysUser.getPassword(),getUserAuthority(sysUser.getStuId()));
    }

    /**
     * 获取用户权限信息 <角色 菜单>
     * @param userId 用户id
     * @return 权限信息
     */
//    public List<GrantedAuthority> getUserAuthority(Long userId){
//
//        //角色(ROLE_admin) 菜单操作权限 sys:user:list,sys:user:save
//        String authority = sysUserService.getUserAuthority(userId);
//
//        //将字符串通过工具类进行解析
//        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
//    }

}