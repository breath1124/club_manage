package cn.zucc.edu.club.service.impl;

import cn.zucc.edu.club.entity.StuClub;
import cn.zucc.edu.club.entity.StuInClub;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.mapper.StuClubMapper;
import cn.zucc.edu.club.service.StuClubService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class StuClubServiceImpl extends ServiceImpl<StuClubMapper, StuClub> implements StuClubService {

    @Autowired
    StuClubMapper stuClubMapper;

    @Override
    public int exitClub(int clubId, int stuId) {
        return stuClubMapper.stuExitClub(clubId, stuId);
    }

    @Override
    public List<StuInClub> searchAllStuInClub(int clubId) {
        return stuClubMapper.searchAllStuInClub(clubId);
    }

    @Override
    public List<StuClub> searchApplyStu(int clubId) {
        return stuClubMapper.searchApplyStuClub(clubId);
    }

}
