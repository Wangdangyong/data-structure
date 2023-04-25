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
 * @since 2023-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TeacherCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 教师id
     */
    private Integer teacherId;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 课程id
     */
    private Integer classId;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 平时分
     */
    private Integer usualScore;

    /**
     * 期末分
     */
    private Integer finalScore;

    /**
     * 总分
     */
    private Integer allScore;

    /**
     * 成绩id
     */
    private Integer scoreId;

    /**
     * 补考标志
     */
    private String flag;

    private Double scorePoint;


}
