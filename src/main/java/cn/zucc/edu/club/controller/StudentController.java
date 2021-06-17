package cn.zucc.edu.club.controller;


import cn.zucc.edu.club.entity.Result;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.service.StudentService;
import cn.zucc.edu.club.service.impl.UserDetailServiceImpl;
import cn.zucc.edu.club.util.JwtTokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@CrossOrigin
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @ApiOperation(value = "注册学生")
    @PostMapping("/add")
    public boolean addStudent(@RequestBody Student student) {
        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().eq(Student::getStuNum, student.getStuNum());
        if(studentService.list(qw).isEmpty()) {
            student.setStuState(0);
            student.setStuPwd(new BCryptPasswordEncoder().encode(student.getStuPwd()));
            return studentService.save(student);
        }
        else
            throw new RuntimeException();
    }

    @ApiOperation(value = "删除学生")
    @PostMapping("/delete")
    public boolean delStudent(@RequestParam("stuId") int id) {
        Student student = findOneStudent(id);
        student.setStuState(1);
        return studentService.saveOrUpdate(student);
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

//    @ApiOperation(value = "学生登录")
//    @GetMapping("/login")
//    public Student loginStudent(@RequestParam("stuNum") String num, @RequestParam("stuPwd") String pwd) {
//        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().eq(Student::getStuNum, num);
//        Student student = studentService.getOne(qw);
//        if(pwd.equals(student.getStuPwd())) {
////            student.getToken();
//            return student;
//        }
//        else
//            return null;
//    }

    @ApiOperation(value = "学生登录")
    @PostMapping("/login")
    public Result loginStudent(@RequestParam("stuNum") String num, @RequestParam("stuPwd") String pwd) {
        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().eq(Student::getStuNum, num);
        Student student = studentService.getOne(qw);
        if(bCryptPasswordEncoder.matches(pwd, student.getStuPwd())) {
            String token = jwtTokenUtils.generateToken(student.getStuNum());
//            student.getToken();
            System.out.println(token);
            Claims clams = jwtTokenUtils.getClaimsByToken(token);
            String x = jwtTokenUtils.getUsername(token);
            String y = jwtTokenUtils.getUserRole(token);
            System.out.println(x);
            System.out.println(y);
            System.out.println(clams);

            return Result.success(student);
        }
        else
            return Result.fail("fail");
    }



    @ApiOperation(value = "根据id查找学生")
    @GetMapping("/findStudentById")
    public Student findOneStudent(@RequestParam("stuId") int id) {
        return studentService.getById(id);
    }


//    @GetMapping("/checkToken")
//    public Boolean checkToken(HttpServletRequest request) {
//        String token = request.getHeader("token");
//
//    }

}

