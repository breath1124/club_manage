package cn.zucc.edu.club.service.impl;

import cn.zucc.edu.club.entity.Club;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.mapper.ClubMapper;
import cn.zucc.edu.club.service.ClubService;
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
public class ClubServiceImpl extends ServiceImpl<ClubMapper, Club> implements ClubService {

    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubMapper clubMapper;

    @Override
    public int getClubMemberSum(Club club) {
        return clubMapper.getClubMemberSum(club);
    }


    @Override
    public PageInfo<Club> findStuByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Club> qw = new QueryWrapper<Club>().lambda().ne(Club::getClubIsStop, 1);
        List<Club> clubs = clubService.list(qw);
        return new PageInfo<>(clubs);
    }

    @Override
    public PageInfo<Club> findStuByPageVague(String name, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Club> qw = new QueryWrapper<Club>().lambda().like(Club::getClubName, name);
        List<Club> clubs = clubService.list(qw);
        for(int i = 0; i < clubs.size(); i++) {
            if(clubs.get(i).getClubIsStop() == 1) {
                clubs.remove(clubs.get(i));
            }
        }
        return new PageInfo<>(clubs);
    }

    @Override
    public PageInfo<Club> findStuByPageTypeVague(String type, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Club> qw = new QueryWrapper<Club>().lambda().eq(Club::getClubType, type);
        List<Club> clubs = clubService.list(qw);
        for(int i = 0; i < clubs.size(); i++) {
            if(clubs.get(i).getClubIsStop() == 1) {
                clubs.remove(clubs.get(i));
            }
        }
        return new PageInfo<>(clubs);
    }

    @Override
    public PageInfo<Club> findStuByPageTypeAndNameVague(String type, String name, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Club> qw = new QueryWrapper<Club>().lambda().and(i -> i.like(Club::getClubType, type).like(Club::getClubName, name).ne(Club::getClubIsStop, 1));
        List<Club> clubs = clubService.list(qw);
        for(int i = 0; i < clubs.size(); i++) {
            if(clubs.get(i).getClubIsStop() == 1) {
                clubs.remove(clubs.get(i));
            }
        }
        return new PageInfo<>(clubs);
    }



}
