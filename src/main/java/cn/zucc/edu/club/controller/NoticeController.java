package cn.zucc.edu.club.controller;


import cn.zucc.edu.club.entity.Activity;
import cn.zucc.edu.club.entity.Notice;
import cn.zucc.edu.club.service.NoticeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "新增通知")
    @PostMapping("/add")
    public boolean addNotice(@RequestBody Notice notice) {
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
    public List<Notice> searchAllNotice() {
        List<Notice> notices = noticeService.list();
        return notices;
    }

    @ApiOperation(value = "根据内容模糊查询某通知")
    @GetMapping("/listVague")
    public List<Notice> searchVagueNotice(@RequestParam("noticeContent") String content) {
        LambdaQueryWrapper<Notice> qw = new QueryWrapper<Notice>().lambda().like(Notice::getNoticeContent, content);
        return noticeService.list(qw);
    }

}

