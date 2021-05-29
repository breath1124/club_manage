package cn.zucc.edu.club.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author tengfei
 * @since 2021-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StuActivity对象", description="")
public class StuActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer stuId;

    private Integer activityId;


}
