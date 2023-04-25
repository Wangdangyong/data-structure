package com.wdy.springbootvue.mapper;

import com.wdy.springbootvue.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
