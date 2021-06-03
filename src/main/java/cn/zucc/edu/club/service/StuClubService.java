package cn.zucc.edu.club.service;

import cn.zucc.edu.club.entity.StuClub;
import cn.zucc.edu.club.entity.StuInClub;
import cn.zucc.edu.club.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
public interface StuClubService extends IService<StuClub> {

    int exitClub(int clubId, int stuId);

    List<StuInClub> searchAllStuInClub(int clubId);

    List<StuClub> searchApplyStu(int clubId);

    StuClub getOneStuInClub(int clubId, int stuId);

}
