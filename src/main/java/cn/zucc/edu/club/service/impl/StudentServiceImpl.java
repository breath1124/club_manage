package cn.zucc.edu.club.service.impl;

import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.mapper.StudentMapper;
import cn.zucc.edu.club.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentService studentService;

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
        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().and(i -> i.like(Student::getStuName, name).ne(Student::getStuState, 1));
//        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().like(Student::getStuName, name);
        List<Student> students = studentService.list(qw);
        return new PageInfo<>(students);
    }

}
