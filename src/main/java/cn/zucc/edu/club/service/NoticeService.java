package cn.zucc.edu.club.service;

import cn.zucc.edu.club.entity.Notice;
import cn.zucc.edu.club.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
public interface NoticeService extends IService<Notice> {

    PageInfo<Notice> findStuByPage(int pageNum, int pageSize);

    PageInfo<Notice> findStuByPageVague(String name, int pageNum, int pageSize);

}
