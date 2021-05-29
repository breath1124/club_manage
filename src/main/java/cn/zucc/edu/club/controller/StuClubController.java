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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
@Api(tags = "学生加入社团接口")
@RestController
@RequestMapping("/stuClub")
public class StuClubController {

    @Autowired
    private StuClubService stuClubService;

    @Autowired
    private ClubService clubService;

    @ApiOperation(value = "学生申请入社")
    @PostMapping("/apply")
    public boolean stuApplyClub(@RequestBody StuClub stuClub) {
        return stuClubService.save(stuClub);
    }

    @ApiOperation(value = "学生退出社团")
    @PostMapping("/delete")
    public int stuExitClub(@RequestParam("clubId") int clubId, @RequestParam("stuId") int stuId) {
        Club club = clubService.getById(clubId);
        club.setClubPeopleCount(club.getClubPeopleCount()-1);
        clubService.saveOrUpdate(club);
        return stuClubService.exitClub(clubId, stuId);
    }

    @ApiOperation(value = "列出某社团内的所有学生")
    @GetMapping("/listAll")
    public List<StuInClub> searchAllStudentInClub(@RequestParam("clubId") int clubId) {
//        LambdaQueryWrapper<S>
        List<StuInClub> stuInClubs = stuClubService.searchAllStuInClub(clubId);
        return stuInClubs;
    }

    @ApiOperation(value = "列出申请加入某社团但还未被同意的所有学生")
    @GetMapping("/listApply")
    public List<StuClub> searchApplyStudentInClub(@RequestParam("clubId") int clubId) {
//        LambdaQueryWrapper<S>
        List<StuClub> students = stuClubService.searchApplyStu(clubId);
        return students;
    }

    @ApiOperation(value = "社长同意某学生加入社团")
    @PostMapping("/pass")
    public boolean presidentPassApply(@RequestBody StuClub stuClub) {
        stuClub.setApplyIsSuccess(1);
        UpdateWrapper uw = new UpdateWrapper<StuClub>().set("apply_is_success",stuClub.getApplyIsSuccess()).
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

}

