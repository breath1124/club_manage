package cn.zucc.edu.club.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2021-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StuApplyClub对象", description="")
public class StuApplyClub implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer clubId;

    private Long stuId;

    private String applyContent;

    private String stuName;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date applyTime;

}
