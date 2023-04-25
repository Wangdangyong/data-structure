package com.wdy.springbootvue.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.Student;
import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.StudentService;
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
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @GetMapping("/list")
    public Result list() {
        List list=studentService.list();
        return Result.sucess(list);

    }

    //新增
    @PostMapping("/save")
    public Result add(@RequestBody Student student) {
        return studentService.save(student) ? Result.sucess() : Result.fall();


    }

    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody Student student) {
        return studentService.updateById(student);
    }

    //新增或修改
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody Student student) {
        return studentService.saveOrUpdate(student);
    }


    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String studentId) {
        return studentService.removeById(studentId) ? Result.sucess() : Result.fall();
    }

    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String studentName = (String) param.get("studentName");
        String studentNumber = (String) param.get("studentNumber");



        Page<Student> page = new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(studentName) && !"null".equals(studentName)) {
            lambdaQueryWrapper.like(Student::getStudentName, studentName);
        }
        if (StringUtils.isNotBlank(studentNumber) && !"null".equals(studentNumber)) {
            lambdaQueryWrapper.like(Student::getStudentNumber, studentNumber);
        }



        IPage result = studentService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(), result.getTotal());
    }
}


