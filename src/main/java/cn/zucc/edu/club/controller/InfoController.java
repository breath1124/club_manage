package cn.zucc.edu.club.controller;

import cn.zucc.edu.club.entity.Activity;
import cn.zucc.edu.club.entity.Club;
import cn.zucc.edu.club.entity.Notice;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.service.ActivityService;
import cn.zucc.edu.club.service.ClubService;
import cn.zucc.edu.club.service.NoticeService;
import cn.zucc.edu.club.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tengfei
 * @since 2021-06-15
 */
@Api(tags = "首页信息接口")
@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/infoSum")
    @ApiOperation(value = "获取学生社团活动通知总数")
    public List<Integer> getInfoCount() {
        PageInfo<Student> students = studentService.findStuByPage(0, 5);
        int studentSum = students.getSize();
        LambdaQueryWrapper<Club> qw = new QueryWrapper<Club>().lambda().ne(Club::getClubIsStop, 1);
        List<Club> clubs = clubService.list(qw);
        int clubSum = clubs.size();
        PageInfo<Activity> activities = activityService.findStuByPage(0, 5);
        int activitySum = activities.getSize();
        PageInfo<Notice> notices = noticeService.findStuByPage(0, 5);
        int noticeSum = notices.getSize();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(studentSum);
        list.add(clubSum);
        list.add(activitySum);
        list.add(noticeSum);

        return list;
    }

    @GetMapping("/clubMemSum")
    @ApiOperation(value = "获取各个社团的人数")
    public Map<Object, Integer> getClubMemSum() {
        Map<Object, Integer> map = new HashMap<>();
        List<Club> clubs = clubService.list();
//        List<Map<Object, Integer>> mapList = new
        int count = 0;
        String clubName;
        for(int i = 0; i < clubs.size(); i++) {
            count = clubService.getClubMemberSum(clubs.get(i));
            clubName = clubs.get(i).getClubName();
            map.put(clubName, count);
        }
        return map;
    }


    @GetMapping("/SortMemSum")
    @ApiOperation(value = "获取不同种类社团的人数")
    public Map<Object, Integer> getSortMemSum() {
        Map<Object, Integer> map = new HashMap<>();
        List<Club> clubs = clubService.list();
//        List<Map<Object, Integer>> mapList = new
        int count = 0;
        String clubType;
        for(int i = 0; i < clubs.size(); i++) {
            count = clubService.getClubMemberSum(clubs.get(i));
            clubType = clubs.get(i).getClubType();
            if(map.containsKey(clubType)) {
                map.put(clubType, map.get(clubType) + count);
            }
            else
                map.put(clubType, count);
        }
        return map;
    }

}
