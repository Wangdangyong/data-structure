package com.wdy.springbootvue.controller;


import com.wdy.springbootvue.entity.Menu;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("/list")
    public Result list(@RequestParam String roleId){
        List list= menuService.lambdaQuery().like(Menu::getMenuRight,roleId).list();
        return Result.sucess(list);

    }

}
