package com.wdy.springbootvue.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.Major;
import com.wdy.springbootvue.entity.TeacherCourse;
import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.MajorService;
import com.wdy.springbootvue.service.TeacherCourseService;
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
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/doScore")
public class TeacherCourseController {
    @Resource
    private TeacherCourseService teacherCourseService;

    @GetMapping("/list")
    public Result list(){
        List list=teacherCourseService.list();
        return Result.sucess(list);

    }
    //新增
    @PostMapping("/save")
    public Result add(@RequestBody TeacherCourse teacherCourse){
        return teacherCourseService.save(teacherCourse)?Result.sucess():Result.fall();


    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody TeacherCourse teacherCourse) {
        System.out.println("ok");
        return teacherCourseService.updateById(teacherCourse);
    }

    //新增或修改
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody TeacherCourse teacherCourse) {
        return teacherCourseService.saveOrUpdate(teacherCourse);
    }



    //删除
    @GetMapping("/del")
    public Result del (@RequestParam String id){
        return teacherCourseService.removeById( id)?Result.sucess():Result.fall();
    }

    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String studentId= (String) param.get("studentId");



        Page<TeacherCourse> page=new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<TeacherCourse> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(studentId)&& !"null".equals(studentId)){
            lambdaQueryWrapper.like(TeacherCourse::getStudentId,studentId);
        }



        IPage result = teacherCourseService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(),result.getTotal());
    }


}
