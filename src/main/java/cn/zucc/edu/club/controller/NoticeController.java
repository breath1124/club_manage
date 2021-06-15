package cn.zucc.edu.club.controller;


import cn.zucc.edu.club.entity.Activity;
import cn.zucc.edu.club.entity.Notice;
import cn.zucc.edu.club.service.NoticeService;
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
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
@Api(tags = "通知接口")
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @ApiOperation(value = "新增通知")
    @PostMapping("/add")
    public boolean addNotice(@RequestBody Notice notice) throws ParseException {
        String noticeTime = df.format(new Date());
        notice.setNoticeTime(df.parse(noticeTime));
        return noticeService.save(notice);
    }

    @ApiOperation(value = "删除通知")
    @PostMapping("/delete")
    public boolean delNotice(@RequestParam("noticeId") int id) {
        return noticeService.removeById(id);
    }

    @ApiOperation(value = "修改通知")
    @PostMapping("/modify")
    public boolean modifyNotice(@RequestBody Notice notice) {
        return noticeService.saveOrUpdate(notice);
    }

    @ApiOperation(value = "列出所有通知")
    @GetMapping("/listAll")
    public PageInfo<Notice> searchAllNotice(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageInfo<Notice> notices = noticeService.findStuByPage(pageNum, pageSize);
        return notices;
    }

    @ApiOperation(value = "根据内容模糊查询某通知")
    @GetMapping("/listVague")
    public PageInfo<Notice> searchVagueNotice(@RequestParam("noticeContent") String content,
                                          @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageInfo<Notice> notices = noticeService.findStuByPageVague(content, pageNum, pageSize);
        return notices;
    }

    @ApiOperation(value = "根据ID查询公告")
    @GetMapping("/listOne")
    public Notice searchOneClub(@RequestParam("clubId") int id) {
        return noticeService.getById(id);
    }

}

