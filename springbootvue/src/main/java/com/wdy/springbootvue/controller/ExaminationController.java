package com.wdy.springbootvue.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.Examination;
import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-23
 */
@RestController
@RequestMapping("/examination")
public class ExaminationController {
    @Autowired
    private ExaminationService examinationService;
    @GetMapping("/list")
    public Result list(){
        List list=examinationService.list();
        return Result.sucess(list);

    }


    //新增
    @PostMapping("/save")
    public Result add(@RequestBody Examination examination){
        return examinationService.save(examination)?Result.sucess():Result.fall();


    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody  Examination examination) {
        return examinationService.updateById(examination);
    }





    //删除
    @GetMapping("/del")
    public Result del (@RequestParam String  id){
        return examinationService.removeById( id)?Result.sucess():Result.fall();
    }


    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String studentId= (String) param.get(" studentId");
        String courseId= (String) param.get("courseId");


        Page<Examination> page=new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<Examination> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(studentId)&& !"null".equals(studentId)){

            lambdaQueryWrapper.like(Examination::getStudentId, studentId);
        }
        if(StringUtils.isNotBlank(courseId)&& !"null".equals(courseId)){
            lambdaQueryWrapper.eq(Examination::getCourseId,courseId);
        }


        IPage result =examinationService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(),result.getTotal());
    }




}
