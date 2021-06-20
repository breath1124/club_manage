package cn.zucc.edu.club.controller;


import cn.zucc.edu.club.entity.Club;
import cn.zucc.edu.club.entity.StuClub;
import cn.zucc.edu.club.entity.StuInClub;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.service.ActivityService;
import cn.zucc.edu.club.service.ClubService;
import cn.zucc.edu.club.service.StuClubService;
import cn.zucc.edu.club.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
@Api(tags = "学生加入社团接口")
@RestController
@RequestMapping("/stuClub")
public class StuClubController {

    @Autowired
    private StuClubService stuClubService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private StudentService studentService;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @ApiOperation(value = "学生申请入社")
    @PostMapping("/apply")
    public boolean stuApplyClub(@RequestBody StuClub stuClub) throws ParseException {
        String applyTime = df.format(new Date());
        stuClub.setApplyTime(df.parse(applyTime));
        return stuClubService.save(stuClub);
    }

    @ApiOperation(value = "学生退出社团")
    @PostMapping("/stuExit")
    public int stuExitClub(@RequestParam("clubId") int clubId, @RequestParam("stuId") int stuId) {
        Club club = clubService.getById(clubId);
        club.setClubPeopleCount(club.getClubPeopleCount()-1);
        clubService.saveOrUpdate(club);
        return stuClubService.exitClub(clubId, stuId);
    }

    @ApiOperation(value = "列出某社团内的所有学生")
    @GetMapping("/listAll")
    public PageInfo<StuInClub> searchAllStudentInClub(@RequestParam("clubId") int clubId,
                                                  @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {

//        PageHelper.startPage(start, size, "stu_club.stu_id asc");
        PageInfo<StuInClub> stuInClubs = stuClubService.findStuByPage(clubId, pageNum, pageSize);
//        PageInfo<StuInClub> page = new PageInfo<>(stuInClubs);
//        System.out.println("11111111111111111"+page.getList());
//        System.out.println(page);
//        System.out.println(page.getTotal());
        return stuInClubs;
    }

    @ApiOperation(value = "列出申请加入某社团但还未被同意的所有学生")
    @GetMapping("/listApply")
    public PageInfo<StuClub> searchApplyStudentInClub(@RequestParam("clubId") int clubId,
                                                  @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
//        LambdaQueryWrapper<S>
        PageInfo<StuClub> students = stuClubService.findStuByPageVague(clubId, pageNum, pageSize);
        return students;
    }

    @ApiOperation(value = "社长同意某学生加入社团")
    @PostMapping("/pass")
    public boolean presidentPassApply(@RequestBody StuClub stuClub) throws ParseException {
        stuClub.setApplyIsSuccess(1);
        String joinTime = df.format(new Date());
        stuClub.setJoinTime(df.parse(joinTime));
        UpdateWrapper uw = new UpdateWrapper<StuClub>().set("apply_is_success",stuClub.getApplyIsSuccess()).
                set("join_time", stuClub.getJoinTime()).
                eq("club_id", stuClub.getClubId()).eq("stu_id", stuClub.getStuId());
        Club club = clubService.getById(stuClub.getClubId());
        club.setClubPeopleCount(club.getClubPeopleCount()+1);
        clubService.saveOrUpdate(club);
        return stuClubService.update(uw);
    }

    @ApiOperation(value = "社长拒绝某学生加入社团")
    @PostMapping("/reject")
    public boolean presidentRejectApply(@RequestBody StuClub stuClub) {
        stuClub.setApplyIsSuccess(2);
        UpdateWrapper uw = new UpdateWrapper<StuClub>().set("apply_is_success",stuClub.getApplyIsSuccess()).
                eq("club_id", stuClub.getClubId()).eq("stu_id", stuClub.getStuId());
        return stuClubService.update(uw);
    }

    @ApiOperation(value = "添加某位社团外同学社长")
    @PostMapping("/addPresident")
    public boolean addPresident(@RequestParam int clubId, @RequestBody Student student) throws ParseException {
        // 首先撤销原先的社长
        StuInClub stuInClub = searchPersident(clubId);
        StuClub stuClub = stuClubService.getOneStuInClub(clubId, stuInClub.getStuId());
        if(stuInClub != null) {
            Student stu = studentService.getById(stuInClub.getStuId());
            stuClub.setStatus("普通成员");
            stu.setRole(3);
            studentService.saveOrUpdate(stu);
            stuClubService.saveOrUpdate(stuClub);
        }

        Club club = clubService.getById(clubId);
        club.setClubPresident(student.getStuName());
        student.setStuIsPresident(club.getClubName());
        student.setRole(2);
        clubService.saveOrUpdate(club);
        studentService.saveOrUpdate(student);
        StuClub stuClub1 = new StuClub();
        stuClub1.setClubId(clubId);
        stuClub1.setStuId(student.getStuId());
        stuClub1.setApplyIsSuccess(1);
        stuClub1.setStatus("社长");
        // 设置加入时间为当前时间
        String joinTime = df.format(new Date());
        stuClub1.setJoinTime(df.parse(joinTime));
        return stuClubService.save(stuClub1);
    }

    @ApiOperation(value = "指定某位社团内同学为社长")
    @PostMapping("/assignPresident")
    public boolean assignPresident(@RequestParam int clubId, @RequestBody StuClub stuClub) throws ParseException {
        StuInClub stuInClub1 = searchPersident(clubId);
        StuClub stuClub1 = stuClubService.getOneStuInClub(clubId, stuClub.getStuId());
        if(stuInClub1 != null) {
            Student stu = studentService.getById(stuInClub1.getStuId());
//            stu.setStuIsPresident(0);
            stuClub1.setStatus("普通成员");
            stu.setRole(3);
            studentService.saveOrUpdate(stu);
        }

        Student student = studentService.getById(stuClub.getStuId());
        Club club = clubService.getById(clubId);
        club.setClubPresident(student.getStuName());
        student.setStuIsPresident(club.getClubName());
        stuClub.setStatus("社长");
        student.setRole(2);
        clubService.saveOrUpdate(club);
        stuClubService.saveOrUpdate(stuClub);
        return studentService.saveOrUpdate(student);

//        StuClub stuClub = stuClubService.getOneStuInClub(clubId, student.getStuId());
//        return stuClubService.saveOrUpdate(stuClub);
    }

    @ApiOperation(value = "查询某社团社长信息")
    @PostMapping("/searchPresident")
    public StuInClub searchPersident(@RequestParam int clubId) {

        List<StuInClub> stuInClubs = stuClubService.searchAllStuInClub(clubId);
        for(int i = 0; i < stuInClubs.size(); i++) {
            if(stuInClubs.get(i).getStuIsPresident() == clubId)
                return stuInClubs.get(i);
        }
        return null;
    }

    @ApiOperation(value = "撤销社长")
    @PostMapping("/deletePresident")
    public boolean deletePersident(@RequestParam("clubId") int clubId, @RequestParam("stuNum") String stuNum) {

        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().eq(Student::getStuNum, stuNum);
        Student student = studentService.getOne(qw);

//        stuClub.setStatus("普通成员");
        Club club = clubService.getById(clubId);
        if(!club.getClubName().equals(student.getStuIsPresident()))
            return false;

        student.setStuIsPresident("");
        club.setClubPresident("");
        student.setRole(3);
        clubService.saveOrUpdate(club);
        return studentService.saveOrUpdate(student);
//        return stuClubService.saveOrUpdate(stuClub);
    }

}

