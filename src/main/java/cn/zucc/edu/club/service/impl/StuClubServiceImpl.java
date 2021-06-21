package cn.zucc.edu.club.service.impl;

import cn.zucc.edu.club.entity.StuApplyClub;
import cn.zucc.edu.club.entity.StuClub;
import cn.zucc.edu.club.entity.StuInClub;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.mapper.StuClubMapper;
import cn.zucc.edu.club.service.StuActivityService;
import cn.zucc.edu.club.service.StuClubService;
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
public class StuClubServiceImpl extends ServiceImpl<StuClubMapper, StuClub> implements StuClubService {

    @Autowired
    StuClubMapper stuClubMapper;

    @Autowired
    private StuClubService stuClubService;

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

    @Override
    public List<StuApplyClub> listAllApplyStu(int clubId) {
        return stuClubMapper.listAllApplyStu(clubId);
    }

    @Override
    public StuClub getOneStuInClub(int clubId, Long stuId) {
        return stuClubMapper.getOneStuInClub(clubId, stuId);
    }

    @Override
    public PageInfo<StuInClub> findStuByPage(int clubId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<StuInClub> stuInClubs = stuClubService.searchAllStuInClub(clubId);
        return new PageInfo<>(stuInClubs);
    }

    @Override
    public PageInfo<StuClub> findStuByPageVague(int clubId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<StuClub> students = stuClubService.searchApplyStu(clubId);
        return new PageInfo<>(students);
    }

    @Override
    public PageInfo<StuApplyClub> findAllApplyStuByPage(int clubId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<StuApplyClub> students = stuClubService.listAllApplyStu(clubId);
        return new PageInfo<>(students);
    }

}
