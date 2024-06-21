package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.QueryPageParam;
import com.example.common.Result;
import com.example.entity.Goodstype;
import com.example.service.IGoodstypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * InnoDB free: 3072 kB 前端控制器
 * </p>
 *
 * @author maso
 * @since 2024-06-20
 */
@RestController
@RequestMapping("/goodstype")
public class GoodstypeController {

    @Resource
    IGoodstypeService goodstypeService;

    //查询所有内容
    @GetMapping("/list")
    public Result list() {
        List list = goodstypeService.lambdaQuery().list();
        return Result.success(list);
    }

    // 根据仓库名查询
    @GetMapping("/findByName")
    public Result findByName(@RequestParam String name) {
        List list = goodstypeService.lambdaQuery().eq(Goodstype::getName, name).list();
        return list.size() > 0 ? Result.success(list) : Result.fail();
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public Result saveOrMod(@RequestBody Goodstype goodstype) {
        return goodstypeService.saveOrUpdate(goodstype) ? Result.success() : Result.fail();
    }

    //删除
    @GetMapping("/delete")
    public Result delete(Integer id) {
        return goodstypeService.removeById(id) ? Result.success() : Result.fail();
    }

    // 前端用这个查询
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<Goodstype> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goodstype> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) lambdaQueryWrapper.like(Goodstype::getName, name);

        IPage result = goodstypeService.pageCC(page, lambdaQueryWrapper);

        return Result.success(result.getTotal(), result.getRecords());
    }
}
