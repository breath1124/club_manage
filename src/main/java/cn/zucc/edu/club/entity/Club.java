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
 * @since 2021-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Club对象", description="")
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "club_id", type = IdType.AUTO)
    private Integer clubId;

    private String clubPresident;

    private String clubType;

    private Integer clubPeopleCount;

    private String clubIntroduce;

    private String clubName;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date clubInit;

    private Integer clubIsStop;


}
