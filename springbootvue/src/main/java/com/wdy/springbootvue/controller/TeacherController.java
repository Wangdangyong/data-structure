package com.wdy.springbootvue.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.Teacher;

import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.TeacherService;

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
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @GetMapping("/list")
    public Result list(){
        List list=teacherService.list();
        return Result.sucess(list);

    }
    //新增
    @PostMapping("/save")
    public Result add(@RequestBody Teacher teacher){
        return teacherService.save(teacher)?Result.sucess():Result.fall();


    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody Teacher teacher) {
        return teacherService.updateById(teacher);
    }

    //新增或修改
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody Teacher teacher) {
        return teacherService.saveOrUpdate(teacher);
    }

    //删除
    @PostMapping("/delete")
    public boolean delete (Integer id){
        return teacherService.removeById(id);
    }

    //删除
    @GetMapping("/del")
    public Result del (@RequestParam String teacherId){
        return teacherService.removeById(teacherId)?Result.sucess():Result.fall();
    }

    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String teacherName= (String) param.get("teacherName");
        String teacherDepId= (String) param.get("teacherDepId");


        Page<Teacher> page=new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(teacherName)&& !"null".equals(teacherName)){
            lambdaQueryWrapper.like(Teacher::getTeacherName,teacherName);
        }
        if(StringUtils.isNotBlank(teacherDepId)&& !"null".equals(teacherDepId)){
            lambdaQueryWrapper.like(Teacher::getTeacherDepId,teacherDepId);
        }


        IPage result = teacherService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(),result.getTotal());
    }

}
