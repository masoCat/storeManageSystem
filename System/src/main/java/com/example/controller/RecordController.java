package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.QueryPageParam;
import com.example.common.Result;
import com.example.entity.Record;
import com.example.service.IRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * <p>
 * InnoDB free: 3072 kB 前端控制器
 * </p>
 *
 * @author maso
 * @since 2024-06-21
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Resource
    IRecordService recordService;

    // 前端用这个查询
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String goods = (String) param.get("goods");
        String goodstype = (String) param.get("goodstype");
        String storage = (String) param.get("storage");

        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Record> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(goods) && !"null".equals(goods)) {

        }
        if (StringUtils.isNotBlank(goods) && !"null".equals(goods)) {

        }
        if (StringUtils.isNotBlank(goods) && !"null".equals(goods)) {

        }
        IPage result = recordService.pageCC(page, lambdaQueryWrapper);

        return Result.success(result.getTotal(), result.getRecords());
    }
}
