package cn.zucc.edu.club.controller;


import cn.zucc.edu.club.entity.StuActivity;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.service.ActivityService;
import cn.zucc.edu.club.service.StuActivityService;
import cn.zucc.edu.club.service.StudentService;
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
@Api(tags = "学生参加活动接口")
@RestController
@RequestMapping("/stuActivity")
public class StuActivityController {

    @Autowired
    private StuActivityService stuActivityService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "学生参加活动")
    @PostMapping("/join")
    public boolean stuJoinActivity(@RequestBody StuActivity stuActivity) {
        return stuActivityService.save(stuActivity);
    }

    @ApiOperation(value = "学生退出活动")
    @PostMapping("/exit")
    public int stuExitActivity(@RequestParam("stuId") int stuId, @RequestParam("activityId") int activityId) {
        return stuActivityService.studentExitActivity(stuId, activityId);
    }

    @ApiOperation(value = "查看参加活动的学生信息")
    @GetMapping("/listAll")
    public List<Student> searchJoinStu(@RequestParam("activityId") int activityId) {
        List<Student> students = stuActivityService.searchJoinStu(activityId);
        return students;
    }


}

