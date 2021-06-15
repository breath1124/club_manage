package cn.zucc.edu.club.controller;


import cn.zucc.edu.club.entity.Activity;
import cn.zucc.edu.club.entity.Admin;
import cn.zucc.edu.club.entity.Club;
import cn.zucc.edu.club.mapper.ActivityMapper;
import cn.zucc.edu.club.service.ActivityService;
import cn.zucc.edu.club.service.AdminService;
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
@Api(tags = "活动接口")
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @ApiOperation(value = "添加活动")
    @PostMapping("/add")
    public boolean addActivity(@RequestBody Activity activity) {
        activity.setActivityStop(0);
        return activityService.save(activity);
    }

    @ApiOperation(value = "删除活动")
    @PostMapping("/delete")
    public boolean delActivity(@RequestParam("activityId") int id) {
        Activity activity = activityService.getById(id);
        activity.setActivityStop(1);
        return activityService.saveOrUpdate(activity);
    }

    @ApiOperation(value = "修改活动")
    @PostMapping("/modify")
    public boolean modifyActivity(@RequestBody Activity activity) {
        return activityService.saveOrUpdate(activity);
    }

    @ApiOperation(value = "列出所有活动")
    @GetMapping("/listAll")
    public PageInfo<Activity> searchAllActivity(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageInfo<Activity> activities = activityService.findStuByPage(pageNum, pageSize);
        return activities;
    }

    @ApiOperation(value = "根据名字模糊查询活动")
    @GetMapping("listVague")
    public PageInfo<Activity> searchVagueActivity(@RequestParam("activityName") String name,
                                                  @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
//        LambdaQueryWrapper<Activity> qw = new QueryWrapper<Activity>().lambda().like(Activity::getActivityName, name);
        PageInfo<Activity> activities = activityService.findStuByPageVague(name, pageNum, pageSize);
        return activities;
    }

    @ApiOperation(value = "根据ID查询活动")
    @GetMapping("/listOne")
    public Activity searchOneActivity(@RequestParam("activityId") int activityId) {
        return activityService.getById(activityId);
    }



}

