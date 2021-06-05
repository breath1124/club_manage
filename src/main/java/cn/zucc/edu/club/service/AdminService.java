package cn.zucc.edu.club.service;

import cn.zucc.edu.club.entity.Admin;
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
public interface AdminService extends IService<Admin> {

    PageInfo<Admin> findStuByPage(int pageNum, int pageSize);

    PageInfo<Admin> findStuByPageVague(String name, int pageNum, int pageSize);
}
