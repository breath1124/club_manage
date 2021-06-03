package cn.zucc.edu.club.mapper;

import cn.zucc.edu.club.entity.StuClub;
import cn.zucc.edu.club.entity.StuInClub;
import cn.zucc.edu.club.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
public interface StuClubMapper extends BaseMapper<StuClub> {

    int stuExitClub(int clubId, int stuId);

    List<StuInClub> searchAllStuInClub(int clubId);

    List<StuClub> searchApplyStuClub(int clubId);

    StuClub getOneStuInClub(int clubId, int stuId);

}
