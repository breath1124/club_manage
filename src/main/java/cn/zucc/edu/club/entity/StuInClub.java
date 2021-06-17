package cn.zucc.edu.club.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StuInClub对象", description="")
public class StuInClub implements Serializable {

    private static final long serialVersionUID = 1L;

    private String stuNum;

    private String stuName;

    private Integer stuAge;

    private String stuSex;

    private String stuTel;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date joinTime;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date applyTime;

    private Integer stuIsPresident;

    private Long stuId;

    private String status;


}
