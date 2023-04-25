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
 * @since 2023-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SelectCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 选课id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 学生名
     */
    private String studentName;

    /**
     * 学生班级
     */
    private String studentClass;


}
