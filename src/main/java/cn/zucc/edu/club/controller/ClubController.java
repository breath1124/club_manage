package cn.zucc.edu.club.controller;


import cn.zucc.edu.club.entity.Club;
import cn.zucc.edu.club.entity.Notice;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.service.ClubService;
import cn.zucc.edu.club.service.StudentService;
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

    @Autowired
    private StudentService studentService;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @ApiOperation(value = "新增社团")
    @PostMapping("/add")
    public boolean addClub(@RequestBody Club club) throws ParseException {

        String stuName = club.getClubPresident();

        if (!(stuName == null) && !stuName.equals("")) {
            LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new QueryWrapper<Student>().lambda().eq(Student::getStuName, stuName);
            Student student = studentService.getOne(studentLambdaQueryWrapper);
            if (student == null)
                throw new RuntimeException();
            if (student.getStuIsPresident() != null && !student.getStuIsPresident().equals(""))
                throw new RuntimeException();
        }

        LambdaQueryWrapper<Club> qw = new QueryWrapper<Club>().lambda().eq(Club::getClubName, club.getClubName());
        if(clubService.list(qw).isEmpty()) {
            String initTime = df.format(new Date());
            club.setClubInit(df.parse(initTime));
            if (!(club.getClubPresident() == null) && !club.getClubName().equals("")) {
                LambdaQueryWrapper<Student> queryWrapper = new QueryWrapper<Student>().lambda().eq(Student::getStuName, club.getClubPresident());
                Student student = studentService.getOne(queryWrapper);
                student.setStuIsPresident(club.getClubName());
                studentService.saveOrUpdate(student);
            }
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
        String clubName = club.getClubName();

        LambdaQueryWrapper<Student> qw = new QueryWrapper<Student>().lambda().eq(Student::getStuIsPresident, clubName);
        Student preStu = studentService.getOne(qw);

        String stuNum = club.getClubPresident();

        String stuName = club.getClubPresident();

        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new QueryWrapper<Student>().lambda().eq(Student::getStuName, stuName);
        Student newStu = studentService.getOne(studentLambdaQueryWrapper);

        if (newStu == null)
            throw new RuntimeException();

//        Student newStu = studentService.getByStuNum(stuNum);
        if(newStu.getStuIsPresident() == null || newStu.getStuIsPresident().equals("")) {
            if (preStu != null) {
                preStu.setRole(3);
                preStu.setStuIsPresident("");
                studentService.saveOrUpdate(preStu);
            }
            newStu.setStuIsPresident(clubName);
            newStu.setRole(2);
            studentService.saveOrUpdate(newStu);
            return clubService.saveOrUpdate(club);
        }
        else if(newStu.getStuIsPresident().equals(clubName))
            return clubService.saveOrUpdate(club);

        else
            throw new RuntimeException();
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

