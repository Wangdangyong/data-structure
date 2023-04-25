package com.wdy.springbootvue.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.wdy.springbootvue.entity.Class;
import com.wdy.springbootvue.entity.ClassCourse;
import com.wdy.springbootvue.entity.Course;
import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.ClassCourseService;
import com.wdy.springbootvue.service.CourseService;
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
 * @since 2023-04-07
 */
@RestController
@RequestMapping("/class-course")
public class ClassCourseController {
    @Resource
    private ClassCourseService classCourseService;

    @Resource
    private CourseService courseService;

    @GetMapping("/list")
    public Result list() {
        List list = classCourseService.list();
        return Result.sucess(list);

    }

    @GetMapping("/class/{classId}")
    public Result findByClass(@PathVariable Integer classId) {
        return Result.sucess(classCourseService.list(new QueryWrapper<ClassCourse>().eq("class_id", classId)));

    }

    //新增
    @PostMapping("/save")
    public Result add(@RequestBody List<ClassCourse> cCourse) {

        classCourseService.saveAll(cCourse);
        return Result.sucess();


    }
    //新增
    @PostMapping("/addClassCourse")
    public Result addClassCourse(@RequestBody ClassCourse classCourse){
        System.out.println(classCourse);
        return classCourseService.save(classCourse)?Result.sucess():Result.fall();


    }
    //查询班级课程表
//    格式
//    tableData:[
//    { section:{num:"第一大节",time:"08:00-09:40"},
//        mon: {course: "英语",room:"A101",teacher:"张三"}
//}
    @GetMapping("/courseTable/{classId}")
    public Result findCourseByTable(@PathVariable Integer classId){
        List<ClassCourse> classCourses=classCourseService.list(new QueryWrapper<ClassCourse>().eq("class_id",classId));

        List<JSONObject> list= CollUtil.newArrayList();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject3 = new JSONObject();
        JSONObject jsonObject4 = new JSONObject();
        JSONObject jsonObject5 = new JSONObject();
        jsonObject1.set("section", JSONUtil.parseObj("{num:\"第一大节\",time:\"08:00-09:40\"}"));
        jsonObject2.set("section",JSONUtil.parseObj("{num:\"第二大节\",time:\"09:50-11::30\"}"));
        jsonObject3.set("section",JSONUtil.parseObj("{num:\"第三大节\",time:\"14:00-15:40\"}"));
        jsonObject4.set("section",JSONUtil.parseObj("{num:\"第四大节\",time:\"15:50-17:30\"}"));
        jsonObject5.set("section",JSONUtil.parseObj("{num:\"第五大节\",time:\"19:00-20:30\"}"));

        list.add(jsonObject1);
        list.add(jsonObject2);
        list.add(jsonObject3);
        list.add(jsonObject4);
        list.add(jsonObject5);

        classCourses.forEach(mm ->{
            Integer courseId=mm.getCourseId();

            String weekDay=mm.getWeekDay();
            Course course = courseService.getById(courseId);
            String section = mm.getSection();
            switch (section){
                case "一":
                    setJson(weekDay,course,jsonObject1);
                    break;
                case "二":
                    setJson(weekDay,course,jsonObject2);
                    break;
                case "三":
                    setJson(weekDay,course,jsonObject3);
                    break;
                case "四":
                    setJson(weekDay,course,jsonObject4);
                    break;
                case "五":
                    setJson(weekDay,course,jsonObject5);
                    break;
            }


        });

        return Result.sucess(list);

    }
    private JSONObject setJson(String weekDay,Course course,JSONObject jsonObject){
        if ("周一".equals(weekDay)){
            jsonObject.set("mon",course);
        }else if ("周二".equals(weekDay)){
            jsonObject.set("tue",course);
        }else if ("周三".equals(weekDay)){
            jsonObject.set("wes",course);
        }
        else if ("周四".equals(weekDay)){
            jsonObject.set("thu",course);
        }
        else if ("周五".equals(weekDay)){
            jsonObject.set("fri",course);
        }
        else if ("周六".equals(weekDay)){
            jsonObject.set("sat",course);
        }
        else if ("周日".equals(weekDay)){
            jsonObject.set("sun",course);
        }

        return jsonObject;
    }

    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody ClassCourse classCourse) {
        return classCourseService.updateById(classCourse);
    }





    //删除
    @GetMapping("/del")
    public Result del (@RequestParam Integer  id){
        System.out.println("ok");
        return classCourseService.removeById(id)?Result.sucess():Result.fall();
    }



    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String classId= (String) param.get("classId");
        String courseId= (String) param.get("courseId");


        Page<ClassCourse> page=new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<ClassCourse> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(classId)&& !"null".equals(classId)){
            lambdaQueryWrapper.like(ClassCourse::getClassId,classId);
        }
        if(StringUtils.isNotBlank(courseId)&& !"null".equals(courseId)){
            lambdaQueryWrapper.like(ClassCourse::getCourseId,courseId);
        }


        IPage result = classCourseService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(),result.getTotal());
    }




}
