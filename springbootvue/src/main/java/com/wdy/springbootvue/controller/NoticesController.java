package com.wdy.springbootvue.controller;


import com.wdy.springbootvue.entity.Course;
import com.wdy.springbootvue.entity.Notices;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.CourseService;
import com.wdy.springbootvue.service.NoticesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/notice")
public class NoticesController {
    @Resource
    private NoticesService noticesService;

    @GetMapping("/list")
    public Result list(){
        List list=noticesService.list();
        return Result.sucess(list);

    }
    //新增
    @PostMapping("/save")
    public Result add(@RequestBody Notices notices){
        return noticesService.save(notices)?Result.sucess():Result.fall();


    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody Notices notices) {
        return noticesService.updateById(notices);
    }





    //删除
    @GetMapping("/del")
    public Result del (@RequestParam String  id){
        return noticesService.removeById(id)?Result.sucess():Result.fall();
    }


}
