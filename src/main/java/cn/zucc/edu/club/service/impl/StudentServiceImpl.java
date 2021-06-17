package cn.zucc.edu.club.service.impl;

import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.mapper.StudentMapper;
import cn.zucc.edu.club.service.StudentService;
import cn.zucc.edu.club.util.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentService studentService;

    @Autowired
    RedisUtil redisUtil;

//    @Override
//    public String getUserAuthorityInfo(Long userId) {
//        Student sysUser = studentService.getById(userId);
//
//        //  ROLE_admin,ROLE_normal,sys:user:list,....
//        String authority = "";
//
//        if (redisUtil.hasKey("GrantedAuthority:" + sysUser.getUsername())) {
//            authority = (String) redisUtil.get("GrantedAuthority:" + sysUser.getUsername());
//
//        } else {
//            // 获取角色编码
//            List<Student> roles = studentService.list(new QueryWrapper<Student>()
//                    .inSql("id", "select role_id from user_role where stu_id = " + userId));
//
//            if (roles.size() > 0) {
//                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
//                authority = roleCodes.concat(",");
//            }
//
//            // 获取菜单操作编码
//            List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
//            if (menuIds.size() > 0) {
//
//                List<SysMenu> menus = sysMenuService.listByIds(menuIds);
//                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
//
//                authority = authority.concat(menuPerms);
//            }
//
//            redisUtil.set("GrantedAuthority:" + sysUser.getUsername(), authority, 60 * 60);
//        }
//
//        return authority;
//    }

    @Override
    public Student getByStuNum(String stuNum) {
        return getOne(new QueryWrapper<Student>().eq("stu_num", stuNum));
    }

    @Override
    public PageInfo<Student> findStuByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().ne(Student::getStuState, 1);
        List<Student> students = studentService.list(qw);
        return new PageInfo<>(students);
    }

    @Override
    public PageInfo<Student> findStuByPageVague(String name, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().and(i -> i.like(Student::getStuNum, name).and(j -> j.eq(Student::getRole, 3)).ne(Student::getStuState, 1));

//        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().and(i -> i.like(Student::getStuName, name).ne(Student::getStuState, 1));
//        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().like(Student::getStuName, name);
        List<Student> students = studentService.list(qw);
        return new PageInfo<>(students);
    }

}
