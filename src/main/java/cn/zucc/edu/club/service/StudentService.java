package cn.zucc.edu.club.service;

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
public interface StudentService extends IService<Student> {

    PageInfo<Student> findStuByPage(int pageNum, int pageSize);

    PageInfo<Student> findStuByPageVague(String name, int pageNum, int pageSize);

}
