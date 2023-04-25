package com.wdy.springbootvue.mapper;

import com.wdy.springbootvue.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-09
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
