package cn.zucc.edu.club.service;

import cn.zucc.edu.club.entity.Club;
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
public interface ClubService extends IService<Club> {

    PageInfo<Club> findStuByPage(int pageNum, int pageSize);

    PageInfo<Club> findStuByPageVague(String name, int pageNum, int pageSize);

    PageInfo<Club> findStuByPageTypeVague(String type, int pageNum, int pageSize);

    PageInfo<Club> findStuByPageTypeAndNameVague(String type, String name, int pageNum, int pageSize);

}
