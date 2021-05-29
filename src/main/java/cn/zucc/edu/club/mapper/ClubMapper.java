package cn.zucc.edu.club.mapper;

import cn.zucc.edu.club.entity.Club;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
@Component
public interface ClubMapper extends BaseMapper<Club> {

    List<Club> selectAll();

    List<Club> selectVague();

    List<Club> selectType();

}
