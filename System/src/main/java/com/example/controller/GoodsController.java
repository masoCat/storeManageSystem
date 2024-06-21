package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.QueryPageParam;
import com.example.common.Result;
import com.example.entity.Goods;
import com.example.service.IGoodsService;
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
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    IGoodsService goodsService;

    // 根据物品名查询
    @GetMapping("/findByName")
    public Result findByName(@RequestParam String name) {
        List list = goodsService.lambdaQuery().eq(Goods::getName, name).list();
        return list.size() > 0 ? Result.success(list) : Result.fail();
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public Result saveOrMod(@RequestBody Goods goods) {
        return goodsService.saveOrUpdate(goods) ? Result.success() : Result.fail();
    }

    //删除
    @GetMapping("/delete")
    public Result delete(Integer id) {
        return goodsService.removeById(id) ? Result.success() : Result.fail();
    }

    // 前端用这个查询
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String storage = (String) param.get("storage");
        String goodstype = (String) param.get("goodstype");

        Page<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) lambdaQueryWrapper.like(Goods::getName, name);
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) lambdaQueryWrapper.eq(Goods::getStorage, storage);
        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)) lambdaQueryWrapper.eq(Goods::getGoodstype, goodstype);

        IPage result = goodsService.pageCC(page, lambdaQueryWrapper);

        return Result.success(result.getTotal(), result.getRecords());
    }
}
