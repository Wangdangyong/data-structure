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
 * @since 2023-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StudentCourses implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生id
     */
    private Integer studentNumber;

    /**
     * 学生班级
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
     * 节数
     */
    private String section;

    /**
     * 已选人数
     */
    private Integer courseNumber;

    /**
     * 最大容量
     */
    private Integer courseMaxNumber;


}
