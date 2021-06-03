package cn.zucc.edu.club.controller;


import cn.zucc.edu.club.entity.Club;
import cn.zucc.edu.club.service.ClubService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
@Api(tags = "社团接口")
@RestController
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @ApiOperation(value = "新增社团")
    @PostMapping("/add")
    public boolean addClub(@RequestBody Club club) {
        LambdaQueryWrapper<Club> qw = new QueryWrapper<Club>().lambda().eq(Club::getClubName, club.getClubName());
        if(clubService.list(qw).isEmpty())
            return clubService.save(club);
        else
            throw new RuntimeException();
    }

    @ApiOperation(value = "删除社团")
    @PostMapping("/delete")
    public boolean stopClub(@RequestParam("clubId") int id) {
        Club club = clubService.getById(id);
        club.setClubIsStop(1);
        return clubService.saveOrUpdate(club);
    }

    @ApiOperation(value = "修改社团")
    @PostMapping("/modify")
    public boolean modify(@RequestBody Club club) {
        return clubService.saveOrUpdate(club);
    }

    @ApiOperation(value = "列出所有社团")
    @GetMapping("/listAll")
    public List<Club> searchAllClub() {
        LambdaQueryWrapper<Club> qw = new QueryWrapper<Club>().lambda().ne(Club::getClubIsStop, 1);
        List<Club> clubs = clubService.list(qw);
        return clubs;
    }

    @ApiOperation(value = "根据名字模糊查询某社团")
    @GetMapping("/listVague")
    public List<Club> searchVagueClub(@RequestParam("clubName") String name) {
        LambdaQueryWrapper<Club> qw = new QueryWrapper<Club>().lambda().like(Club::getClubName, name);
        List<Club> clubs = clubService.list(qw);
        for(int i = 0; i < clubs.size(); i++) {
            if(clubs.get(i).getClubIsStop() == 1) {
                clubs.remove(clubs.get(i));
            }
        }
        return clubs;
    }


    @ApiOperation(value = "根据类型查询社团")
    @GetMapping("/listType")
    public List<Club> searchTypeClub(@RequestParam("clubType") String type) {
        LambdaQueryWrapper<Club> qw = new QueryWrapper<Club>().lambda().eq(Club::getClubType, type);
        List<Club> clubs = clubService.list(qw);
        for(int i = 0; i < clubs.size(); i++) {
            if(clubs.get(i).getClubIsStop() == 1) {
                clubs.remove(clubs.get(i));
            }
        }
        return clubs;
    }

    @ApiOperation(value = "根据ID查询社团")
    @GetMapping("/listOne")
    public Club searchOneClub(@RequestParam("clubId") int id) {
        return clubService.getById(id);
    }


}

