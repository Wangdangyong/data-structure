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
 * @since 2023-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生id
     */
    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer studentId;

    /**
     * 学生学号
     */
    private Integer studentNumber;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 班级id
     */
    private Integer classId;

    /**
     * 专业id
     */
    private Integer studentMajorId;

    /**
     * 年级
     */
    private String studentGrade;


}
