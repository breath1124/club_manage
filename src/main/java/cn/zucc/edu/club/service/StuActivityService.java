package cn.zucc.edu.club.service;

import cn.zucc.edu.club.entity.StuActivity;
import cn.zucc.edu.club.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
public interface StuActivityService extends IService<StuActivity> {

    int studentExitActivity(int stuId, int activityId);

    List<Student> searchJoinStu(int activityId);

    PageInfo<Student> findStuByPage(int activityId, int pageNum, int pageSize);
}
