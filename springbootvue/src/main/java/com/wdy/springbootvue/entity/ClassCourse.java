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
 * @since 2023-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClassCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 选课id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 班级id
     */
    private Integer classId;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 日期
     */
    private String weekDay;

    /**
     * 第几节
     */
    private String section;


}
