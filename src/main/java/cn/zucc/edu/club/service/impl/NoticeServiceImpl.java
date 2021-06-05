package cn.zucc.edu.club.service.impl;

import cn.zucc.edu.club.entity.Notice;
import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.mapper.NoticeMapper;
import cn.zucc.edu.club.service.NoticeService;
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
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeService noticeService;

    @Override
    public PageInfo<Notice> findStuByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> notices = noticeService.list();
        return new PageInfo<>(notices);
    }

    @Override
    public PageInfo<Notice> findStuByPageVague(String content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<Notice> qw = new QueryWrapper<Notice>().lambda().like(Notice::getNoticeContent, content);
        List<Notice> notices = noticeService.list(qw);
        return new PageInfo<>(notices);
    }

}
