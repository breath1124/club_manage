package cn.zucc.edu.club.mapper;

import cn.zucc.edu.club.entity.StuActivity;
import cn.zucc.edu.club.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
@Component
public interface StuActivityMapper extends BaseMapper<StuActivity> {

    int studentExitActivity(int stuId, int activityId);

    List<Student> searchJoinStu(int activityId);
}
