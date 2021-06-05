package cn.zucc.edu.club.service.impl;

import cn.zucc.edu.club.entity.StuActivity;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.mapper.StuActivityMapper;
import cn.zucc.edu.club.service.StuActivityService;
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
public class StuActivityServiceImpl extends ServiceImpl<StuActivityMapper, StuActivity> implements StuActivityService {

    @Autowired
    StuActivityMapper stuActivityMapper;

    @Autowired
    private StuActivityService stuActivityService;

    @Override
    public int studentExitActivity(int stuId, int activityId) {
        return stuActivityMapper.studentExitActivity(stuId, activityId);
    }

    @Override
    public List<Student> searchJoinStu(int activityId) {
        return stuActivityMapper.searchJoinStu(activityId);
    }

    @Override
    public PageInfo<Student> findStuByPage(int activityId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Student> students = stuActivityService.searchJoinStu(activityId);
        return new PageInfo<>(students);
    }
}
