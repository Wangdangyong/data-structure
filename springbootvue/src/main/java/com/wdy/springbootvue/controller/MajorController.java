package com.wdy.springbootvue.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.Major;
import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.MajorService;
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
 * @since 2023-04-06
 */
@RestController
@RequestMapping("/major")
public class MajorController {
    @Resource
    private MajorService majorService;

    @GetMapping("/list")
    public Result list(){
        List list=majorService.list();
        return Result.sucess(list);

    }
    //新增
    @PostMapping("/save")
    public Result add(@RequestBody Major major){
        return majorService.save(major)?Result.sucess():Result.fall();


    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody Major major) {
        return majorService.updateById(major);
    }

    //新增或修改
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody Major major) {
        return majorService.saveOrUpdate(major);
    }



    //删除
    @GetMapping("/del")
    public Result del (@RequestParam String majorId){
        return majorService.removeById( majorId)?Result.sucess():Result.fall();
    }

    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String majorName= (String) param.get("majorName");
        String majorTeacher= (String) param.get("majorTeacher");



        Page<Major> page=new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<Major> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(majorName)&& !"null".equals(majorName)){
            lambdaQueryWrapper.like(Major::getMajorName,majorName);
        }
        if(StringUtils.isNotBlank(majorTeacher)&& !"null".equals(majorTeacher)){
            lambdaQueryWrapper.like(Major::getMajorTeacher,majorTeacher);
        }


        IPage result = majorService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(),result.getTotal());
    }



}
