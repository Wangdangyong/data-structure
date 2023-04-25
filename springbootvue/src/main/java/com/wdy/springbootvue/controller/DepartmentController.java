package com.wdy.springbootvue.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.Department;
import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.DepartmentService;
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
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @GetMapping("/list")
    public Result  list(){
        List list=departmentService.list();
        return Result.sucess(list);


    }
    //新增
    @PostMapping("/save")
    public Result add(@RequestBody Department department){
        return departmentService.save(department)?Result.sucess():Result.fall();


    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody Department department) {
        return departmentService.updateById(department);
    }





    //删除
    @GetMapping("/del")
    public Result del (@RequestParam String  depId){
        return departmentService.removeById( depId)?Result.sucess():Result.fall();
    }

    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String depName= (String) param.get("depName");


        Page<Department> page=new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<Department> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(depName)&& !"null".equals(depName)){
            lambdaQueryWrapper.like(Department::getDepName,depName);
        }


        IPage result = departmentService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(),result.getTotal());
    }



}
