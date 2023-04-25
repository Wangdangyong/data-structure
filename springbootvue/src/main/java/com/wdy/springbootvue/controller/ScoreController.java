package com.wdy.springbootvue.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.Class;
import com.wdy.springbootvue.entity.Score;
import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.ClassService;
import com.wdy.springbootvue.service.ScoreService;
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
@RequestMapping("/score")
public class ScoreController {
    @Resource
    private ScoreService scoreService;

    @GetMapping("/list")
    public Result list(){
        List list=scoreService.list();
        return Result.sucess(list);

    }

    //新增
    @PostMapping("/save")
    public Result add(@RequestBody Score score){
        return scoreService.save(score)?Result.sucess():Result.fall();


    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody Score score) {
        return scoreService.updateById(score);
    }





    //删除
    @GetMapping("/del")
    public Result del (@RequestParam Integer id){
        return scoreService.removeById( id)?Result.sucess():Result.fall();
    }


    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String studentName= (String) param.get("studentName");
        String studentId= (String) param.get("studentId");


        Page<Score> page=new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<Score> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(studentName)&& !"null".equals(studentName)){

            lambdaQueryWrapper.like(Score::getStudentName,studentName);
        }
        if(StringUtils.isNotBlank(studentId)&& !"null".equals(studentId)){

            lambdaQueryWrapper.like(Score::getStudentId,studentId);
        }



        IPage result = scoreService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(),result.getTotal());
    }



}
