package com.wdy.springbootvue.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdy.springbootvue.entity.Menu;
import com.wdy.springbootvue.entity.User;
import com.wdy.springbootvue.pojo.QueryPage;
import com.wdy.springbootvue.pojo.Result;
import com.wdy.springbootvue.service.MenuService;
import com.wdy.springbootvue.service.UserService;
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
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;

    @GetMapping("/list")
    public Result list() {
        List list=userService.list();
        return Result.sucess(list);

    }
    //新增
    @PostMapping("/save")
    public Result add(@RequestBody User user) {
        return userService.save(user) ? Result.sucess() : Result.fall();


    }

    //修改
    @PostMapping("/update")
    public boolean update(@RequestBody User user) {
        return userService.updateById(user);
    }

    //新增或修改
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    //删除
    @PostMapping("/delete")
    public boolean delete(Integer userId) {
        return userService.removeById(userId);
    }

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String userId) {
        return userService.removeById(userId) ? Result.sucess() : Result.fall();
    }

    /**
     * 用于接收分页查询的参数
     *
     * @return
     */
    @PostMapping("/listPage")
    public Result listPageT(@RequestBody QueryPage queryPage) {
        HashMap param = queryPage.getParam();
        String userName = (String) param.get("userName");


        Page<User> page = new Page();
        page.setCurrent(queryPage.getPageNum());
        page.setSize(queryPage.getPageSize());

        //查询条件构造器
        //isNotBlank排除空格参数
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userName) && !"null".equals(userName)) {
            lambdaQueryWrapper.like(User::getUserName, userName);
        }


        IPage result = userService.page(page, lambdaQueryWrapper);

        //将结果返回到前端
        return Result.sucess(result.getRecords(), result.getTotal());
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        List<User> list = userService.lambdaQuery()
                .eq(User::getUserAccount, user.getUserAccount())
                .eq(User::getUserPassword, user.getUserPassword())
                .eq(User::getUserRole, user.getUserRole()).list();
        if (list.size() > 0) {
            User user1 = (User) list.get(0);
            List<Menu> menuList = menuService.lambdaQuery().like(Menu::getMenuRight, user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("menu", menuList);
            res.put("user", user1);
            return Result.sucess(res);

        }
        return Result.fall();


    }


}
