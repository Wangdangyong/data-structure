package com.wdy.springbootvue.entity;

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
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名字
     */
    private String menuName;

    /**
     * 菜单级别
     */
    private String menuLevel;

    /**
     * 菜单的父code
     */
    private String menuParentCode;

    /**
     * 点击触发的函数
     */
    private String menuClick;

    /**
     * 权限 admin：管理员 teacher：教师 student：学生
     */
    private String menuRight;

    private String menuComponent;

    private String menuIcon;


}
