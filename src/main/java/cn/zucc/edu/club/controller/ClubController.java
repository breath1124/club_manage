package cn.zucc.edu.club.controller;


import cn.zucc.edu.club.entity.Club;
import cn.zucc.edu.club.service.ClubService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @ApiOperation(value = "新增社团")
    @PostMapping("/add")
    public boolean addClub(@RequestBody Club club) throws ParseException {
        LambdaQueryWrapper<Club> qw = new QueryWrapper<Club>().lambda().eq(Club::getClubName, club.getClubName());
        if(clubService.list(qw).isEmpty()) {
            String initTime = df.format(new Date());
            club.setClubInit(df.parse(initTime));
            return clubService.save(club);
        }
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
    public PageInfo<Club> searchAllClub(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageInfo<Club> clubs = clubService.findStuByPage(pageNum, pageSize);
        return clubs;
    }

    @ApiOperation(value = "根据名字模糊查询某社团")
    @GetMapping("/listVague")
    public PageInfo<Club> searchVagueClub(@RequestParam("clubName") String name,
                                          @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageInfo<Club> clubs = clubService.findStuByPageVague(name, pageNum, pageSize);
        return clubs;
    }


    @ApiOperation(value = "根据类型查询社团")
    @GetMapping("/listType")
    public PageInfo<Club> searchTypeClub(@RequestParam("clubType") String type,
                                         @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageInfo<Club> clubs = clubService.findStuByPageTypeVague(type, pageNum, pageSize);
        return clubs;
    }

    @ApiOperation(value = "根据类型和名字模糊查询社团")
    @GetMapping("/listTypeAndName")
    public PageInfo<Club> searchTypeClub(@RequestParam("clubType") String type,
                                     @RequestParam("clubName") String name,
                                     @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageInfo<Club> clubs = clubService.findStuByPageTypeAndNameVague(type, name, pageNum, pageSize);
        return clubs;
    }

    @ApiOperation(value = "根据ID查询社团")
    @GetMapping("/listOne")
    public Club searchOneClub(@RequestParam("clubId") int id) {
        return clubService.getById(id);
    }


}

