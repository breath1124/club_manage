package cn.zucc.edu.club.service.impl;

import cn.zucc.edu.club.entity.Student;
import cn.zucc.edu.club.mapper.StudentMapper;
import cn.zucc.edu.club.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
