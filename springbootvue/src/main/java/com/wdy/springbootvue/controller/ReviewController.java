package com.wdy.springbootvue.controller;


import com.wdy.springbootvue.entity.Notices;
import com.wdy.springbootvue.entity.Review;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.NoticesService;
import com.wdy.springbootvue.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 王当勇
 * @since 2023-04-22
 */
@RestController
@RequestMapping("/review")
public class ReviewController {
    @Resource
    private ReviewService reviewService;

    @GetMapping("/list")
    public Result list(){
        List list=reviewService.list();
        return Result.sucess(list);

    }
    //新增
    @PostMapping("/save")
    public Result add(@RequestBody Review review){
        return reviewService.save(review)?Result.sucess():Result.fall();


    }
    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody Review review) {
        return reviewService.updateById(review);
    }





    //删除
    @GetMapping("/del")
    public Result del (@RequestParam String  id){
        return reviewService.removeById(id)?Result.sucess():Result.fall();
    }


}
