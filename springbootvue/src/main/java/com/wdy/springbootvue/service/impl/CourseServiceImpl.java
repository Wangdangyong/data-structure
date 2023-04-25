package com.wdy.springbootvue.service.impl;

import com.wdy.springbootvue.entity.Course;
import com.wdy.springbootvue.mapper.CourseMapper;
import com.wdy.springbootvue.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-07
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

}
