package com.wdy.springbootvue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 教务管理员编号
     */
    private Integer userSysId;

    /**
     * 教师编号
     */
    private Integer userTeaId;

    /**
     * 学生学号
     */
    private Integer userStuId;

    /**
     * 角色 admin：教务管理员编号 teacher：教师编号 student：学生学号
     */
    private String userRole;

    /**
     * 角色id
     */
    @TableField("roleId")
    private Integer roleId;


}
