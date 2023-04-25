package com.wdy.springbootvue.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wdy.springbootvue.entity.ClassCourse;
import com.wdy.springbootvue.mapper.ClassCourseMapper;
import com.wdy.springbootvue.service.ClassCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-23
 */
@Service
public class ClassCourseServiceImpl extends ServiceImpl<ClassCourseMapper, ClassCourse> implements ClassCourseService {
    @Transactional
    @Override
    //批量保存

    public void saveAll(List<ClassCourse> cCourse){
        Integer classId=cCourse.get(0).getClassId();
        remove(new UpdateWrapper<ClassCourse>().eq("class_id",classId));
        saveBatch(cCourse);
    }

}
