package com.wdy.springbootvue.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.Course;
import com.wdy.springbootvue.entity.StudentCourses;
import com.wdy.springbootvue.entity.User;
import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.CourseService;
import com.wdy.springbootvue.service.MenuService;
import com.wdy.springbootvue.service.StudentCoursesService;
import com.wdy.springbootvue.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-21
 */
@RestController
@RequestMapping("/student-courses")
public class StudentCoursesController {
    @Resource
    private CourseService courseService;
    @Resource
    private StudentCoursesService studentCoursesService;


    @GetMapping("/list")
    public Result list() {
        List list= studentCoursesService.list();
        return Result.sucess(list);

    }
    //新增
    @PostMapping("/save")
    public Result add(@RequestBody StudentCourses studentCourses) {
        Course course=courseService.getById(studentCourses.getCourseId());

        int n=course.getCourseNumber();
        if(n<course.getCourseMaxNumber()){
            n++;
            course.setCourseNumber(n);
            courseService.updateById(course);

        }


        return  studentCoursesService.save(studentCourses) ? Result.sucess() : Result.fall();


    }

    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody StudentCourses studentCourses) {
        return  studentCoursesService.updateById(studentCourses);
    }


    //删除
    @PostMapping("/delete")
    public boolean delete(Integer id) {
        return studentCoursesService.removeById(id);
    }

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return studentCoursesService.removeById(id) ? Result.sucess() : Result.fall();
    }

    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String studentNumber = (String) param.get("studentNumber");


        Page<StudentCourses> page = new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<StudentCourses> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(studentNumber) && !"null".equals(studentNumber)) {
            lambdaQueryWrapper.like(StudentCourses::getStudentNumber, studentNumber);
        }


        IPage result = studentCoursesService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(), result.getTotal());
    }

}
