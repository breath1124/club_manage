package cn.zucc.edu.club.controller;


import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
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
@Api(tags = "学生接口")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "注册学生")
    @PostMapping("/add")
    public boolean addStudent(@RequestBody Student student) {
        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().eq(Student::getStuNum, student.getStuNum());
        if(studentService.list(qw).isEmpty())
            return studentService.save(student);
        else
            throw new RuntimeException();
    }

    @ApiOperation(value = "删除学生")
    @PostMapping("/delete")
    public boolean delStudent(@RequestParam("stuId") int id) {
        return studentService.removeById(id);
    }

    @ApiOperation(value = "修改学生")
    @PostMapping("/modify")
    public boolean modifyStudent(@RequestBody Student student) {
        return studentService.saveOrUpdate(student);
    }


    @ApiOperation(value = "列出所有学生")
    @GetMapping("/listAll")
    public PageInfo<Student> searchAllStudent(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
//        List<Student> students = studentService.list();
        PageInfo<Student> students = studentService.findStuByPage(pageNum, pageSize);
        return students;
    }

    @ApiOperation(value = "模糊查询学生")
    @GetMapping("/listVague")
    public PageInfo<Student> searchVagueStudent(@RequestParam("stuName") String name,
                                            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageInfo<Student> students = studentService.findStuByPageVague(name, pageNum, pageSize);
        return students;
    }

    @ApiOperation(value = "学生登录")
    @GetMapping("/login")
    public Student loginStudent(@RequestParam("stuNum") String num, @RequestParam("stuPwd") String pwd) {
        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().eq(Student::getStuNum, num);
        Student student = studentService.getOne(qw);
        if(pwd.equals(student.getStuPwd()))
            return student;
        else
            return null;
    }

}

