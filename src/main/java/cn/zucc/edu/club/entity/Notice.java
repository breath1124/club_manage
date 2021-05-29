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
@ApiModel(value="Notice对象", description="")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer noticeId;

    private Integer adminId;

    private String noticeContent;

    private String noticeTitle;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date noticeTime;


}
