package cn.zucc.edu.club.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "stu_id", type = IdType.AUTO)
    private Long stuId;

    private String stuNum;

    private String stuName;

    private Integer stuAge;

    private String stuSex;

    private String stuTel;

    private String stuIsPresident;

    private String stuPwd;

    private Integer stuState;

    private Integer role;

}
