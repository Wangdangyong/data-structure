package com.wdy.springbootvue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系id
     */
    @TableId(value = "dep_id", type = IdType.AUTO)
    private Integer depId;

    /**
     * 院系编号
     */
    private Integer depNo;

    /**
     * 系名称
     */
    private String depName;

    /**
     * 专业数
     */
    private Integer depMajorCount;

    /**
     * 教师人数
     */
    private Integer depTeaCount;


}
