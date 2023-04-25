package com.wdy.springbootvue.service;

import com.wdy.springbootvue.entity.ClassCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-23
 */
public interface ClassCourseService extends IService<ClassCourse> {
    void saveAll(List<ClassCourse> cCourse);

}
