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
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 任课教师id
     */
    private Integer courseTeacherId;

    /**
     * 课程编号
     */
    private String courseNo;

    /**
     * 所属系
     */
    private String courseDepId;

    /**
     * 年级
     */
    private String courseGrade;

    /**
     * 教室
     */
    private String room;

    /**
     * 学分
     */
    private Integer courseCredit;

    /**
     * 日期
     */
    private String weekDay;

    /**
     * 第几节
     */
    private String section;

    /**
     * 是否必修
     */
    private String type;

    /**
     * 已选人数 
     */
    private Integer courseNumber;

    /**
     * 最大容量 
     */
    private Integer courseMaxNumber;


}
