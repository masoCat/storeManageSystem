package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Goods;

/**
 * <p>
 * InnoDB free: 3072 kB 服务类
 * </p>
 *
 * @author maso
 * @since 2024-06-20
 */
public interface IGoodsService extends IService<Goods> {
    IPage pageCC(IPage<Goods> page, Wrapper wrapper);
}
