package com.wdy.springbootvue.service.impl;

import com.wdy.springbootvue.entity.Department;
import com.wdy.springbootvue.mapper.DepartmentMapper;
import com.wdy.springbootvue.service.DepartmentService;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}
