package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.QueryPageParam;
import com.example.common.Result;
import com.example.entity.Menu;
import com.example.entity.User;
import com.example.service.IMenuService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * InnoDB free: 3072 kB 前端控制器
 * </p>
 *
 * @author maso
 * @since 2024-06-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMenuService menuService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        List list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.success(list) : Result.fail();
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        List list = userService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword())
                .list();

        if (list.size() > 0) {
            User user1 = (User) list.get(0);
            List Menulist = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",Menulist);
            return Result.success(res);
        }
        return Result.fail();
    }

    //新增
    @PostMapping("/save")
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public Result saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user) ? Result.success() : Result.fail();
    }

    //删除
    @GetMapping("/delete")
    public Result delete(Integer id) {
        return userService.removeById(id) ? Result.success() : Result.fail();
    }

    //查询（模糊，匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(user.getName())) {
            lambdaQueryWrapper.like(User::getName, user.getName());
        }
        return Result.success(userService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName, name);

        IPage result = userService.page(page, lambdaQueryWrapper);

        System.out.println("总页数：" + result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        IPage result = userService.pageC(page);

        System.out.println("总页数：" + result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageCC")
    public List<User> listPageCC(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName, name);

        IPage result = userService.pageCC(page, lambdaQueryWrapper);

        System.out.println("总页数：" + result.getTotal());

        return result.getRecords();
    }

    // 前端用这个查询
    @PostMapping("/listPageSuc")
    public Result listPageSuc(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String sex = (String) param.get("sex");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) lambdaQueryWrapper.like(User::getName, name);
        if (StringUtils.isNotBlank(sex)) lambdaQueryWrapper.eq(User::getSex, sex);

        IPage result = userService.pageCC(page, lambdaQueryWrapper);

        return Result.success(result.getTotal(), result.getRecords());
    }
}
