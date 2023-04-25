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
public class Major implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 专业id
     */
    @TableId(value = "major_id", type = IdType.AUTO)
    private Integer majorId;

    /**
     * 专业编号
     */
    private Integer majorNo;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 专业指导教师
     */
    private String majorTeacher;

    /**
     * 院系id
     */
    private Integer majorDepId;


}
