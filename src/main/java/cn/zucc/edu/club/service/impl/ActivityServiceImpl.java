package cn.zucc.edu.club.service.impl;

import cn.zucc.edu.club.entity.Activity;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.mapper.ActivityMapper;
import cn.zucc.edu.club.service.ActivityService;
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
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Autowired
    private ActivityService activityService;

    @Override
    public PageInfo<Activity> findStuByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Activity> qw = new QueryWrapper<Activity>().not(item ->item.eq("activity_stop",1));
        List<Activity> activities = activityService.list(qw);
        return new PageInfo<>(activities);
    }

    @Override
    public PageInfo<Activity> findStuByPageVague(String name, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Activity> qw = new QueryWrapper<Activity>().lambda().like(Activity::getActivityName, name).ne(Activity::getActivityStop, 1);
        List<Activity> activities = activityService.list(qw);
        return new PageInfo<>(activities);
    }

}
