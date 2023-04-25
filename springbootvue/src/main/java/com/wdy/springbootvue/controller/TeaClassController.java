package com.wdy.springbootvue.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.Class;
import com.wdy.springbootvue.entity.TeaClass;
import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.ClassService;
import com.wdy.springbootvue.service.TeaClassService;
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
 * @since 2023-04-14
 */
@RestController
@RequestMapping("/tea-class")
public class TeaClassController {
    @Resource
     private TeaClassService teaClassService;

    @GetMapping("/list")
    public Result list(){
        List list=teaClassService.list();
        return Result.sucess(list);

    }


    //新增
    @PostMapping("/save")
    public Result add(@RequestBody TeaClass teaClass){
        return
        teaClassService.save(teaClass)?Result.sucess():Result.fall();


    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody TeaClass teaClass) {
        return
        teaClassService.updateById(teaClass);
    }





    //删除
    @GetMapping("/del")
    public Result del (@RequestParam String  id){
        return
        teaClassService.removeById( id)?Result.sucess():Result.fall();
    }


    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();



        Page<TeaClass> page=new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<TeaClass> lambdaQueryWrapper=new LambdaQueryWrapper<>();



        IPage result =teaClassService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(),result.getTotal());
    }


}
