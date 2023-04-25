package com.wdy.springbootvue.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.Class;
import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;


import com.wdy.springbootvue.service.ClassService;
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
@RequestMapping("/class")
public class ClassController {
    @Resource
    private ClassService classService;

    @GetMapping("/list")
    public Result list(){
        List list=classService.list();
        return Result.sucess(list);

    }
    @GetMapping("/list/{classId}")
    public Result findByClass(@PathVariable Integer classId) {
        List list=classService.list(new QueryWrapper<Class>().eq("class_id", classId));

        return Result.sucess(list);

    }

    //新增
    @PostMapping("/save")
    public Result add(@RequestBody Class clazz){
        return classService.save(clazz)?Result.sucess():Result.fall();


    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody Class clazz) {
        return classService.updateById(clazz);
    }





    //删除
    @GetMapping("/del")
    public Result del (@RequestParam String  classId){
        return classService.removeById( classId)?Result.sucess():Result.fall();
    }


    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String className= (String) param.get("className");
        String classId= (String) param.get("classId");


        Page<Class> page=new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<Class> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(className)&& !"null".equals(className)){

            lambdaQueryWrapper.like(Class::getClassName,className);
        }
        if(StringUtils.isNotBlank(classId)&& !"null".equals(classId)){
            lambdaQueryWrapper.eq(Class::getClassId,classId);
        }


        IPage result = classService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(),result.getTotal());
    }



}
