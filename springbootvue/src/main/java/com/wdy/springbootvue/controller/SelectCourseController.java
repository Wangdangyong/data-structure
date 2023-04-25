package com.wdy.springbootvue.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.SelectCourse;

import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.SelectCourseService;
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
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/select-course")
public class SelectCourseController {
    @Resource
    private SelectCourseService selectCourseService;

    @GetMapping("/list")
    public List<SelectCourse> list(){
        return selectCourseService.list();

    }
    //新增
    @PostMapping("/save")
    public Result add(@RequestBody SelectCourse selectCourse){
        return selectCourseService.save(selectCourse)?Result.sucess():Result.fall();


    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody SelectCourse selectCourse) {
        return selectCourseService.updateById(selectCourse);
    }





    //删除
    @GetMapping("/del")
    public Result del (@RequestParam String  selectCourseId){
        return selectCourseService.removeById( selectCourseId)?Result.sucess():Result.fall();
    }

    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String courseName= (String) param.get("courseName");
        String studentName= (String) param.get("studentName");


        Page<SelectCourse> page=new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<SelectCourse> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(courseName)&& !"null".equals(courseName)){
            lambdaQueryWrapper.like(SelectCourse::getCourseName,courseName);
        }


        if(StringUtils.isNotBlank(studentName)&& !"null".equals(studentName)){
            lambdaQueryWrapper.like(SelectCourse::getStudentName,studentName);
        }


        IPage result = selectCourseService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(),result.getTotal());
    }



}
