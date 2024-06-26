package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.QueryPageParam;
import com.example.common.Result;
import com.example.entity.Goods;
import com.example.entity.Info;
import com.example.entity.Record;
import com.example.service.IGoodsService;
import com.example.service.IInfoService;
import com.example.service.IRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

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
    @Resource
    IGoodsService goodsService;
    @Resource
    IInfoService iInfoService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Record record) {
        Goods goods = goodsService.getById(record.getGoods());
        int num = record.getCount();

        // 出库
        if ("2".equals(record.getAction())) {
            num = -num;
            record.setCount(num);
        }

        // 入库
        int newnum = goods.getCount() + num;
        goods.setCount(newnum);
        goodsService.updateById(goods);

        // 库存数量不足发送通知预警
        List list = iInfoService.lambdaQuery().eq(Info::getGoods, record.getGoods()).list();

        if (goods.getCount() < 50 && list.size() == 0) {
            Info info = new Info();
            info.setInfo("货物：" + goods.getName() + "    库存数量不足50");
            info.setType(0);
            info.setGoods(record.getGoods());
            iInfoService.save(info);
        }
        // 库存足够则删除通知
        if (goods.getCount() >= 50 && list.size() > 0) {
            Info info = (Info) list.get(0);
            iInfoService.removeById(info.getId());
        }

        return recordService.save(record) ? Result.success() : Result.fail();
    }

    // 前端用这个查询
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String goodstype = (String) param.get("goodstype");
        String storage = (String) param.get("storage");
        String roleId = (String) param.get("roleId");
        String userid = (String) param.get("userid");

        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply(" r.goods = g.id and g.storage = s.id and g.goodstype = t.id ");

        if ("2".equals(roleId)) {
            queryWrapper.apply(" r.userId = " + userid);
        }
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like("g.name", name);
        }
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
            queryWrapper.eq("s.id", storage);
        }
        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)) {
            queryWrapper.eq("t.id", goodstype);
        }
        IPage result = recordService.pageCC(page, queryWrapper);

        return Result.success(result.getTotal(), result.getRecords());
    }
}
