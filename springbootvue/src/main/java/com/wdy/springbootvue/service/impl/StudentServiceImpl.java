package com.wdy.springbootvue.service.impl;

import com.wdy.springbootvue.entity.Student;
import com.wdy.springbootvue.mapper.StudentMapper;
import com.wdy.springbootvue.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-09
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
