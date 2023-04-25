package com.wdy.springbootvue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
public class Examination implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 监考教师id
     */
    private Integer teacherId;

    /**
     * 考试教室
     */
    private String room;

    /**
     * 考试时间
     */
    private LocalDate time;


}
