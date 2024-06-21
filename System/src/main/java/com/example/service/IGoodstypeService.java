package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Goodstype;

/**
 * <p>
 * InnoDB free: 3072 kB 服务类
 * </p>
 *
 * @author maso
 * @since 2024-06-20
 */
public interface IGoodstypeService extends IService<Goodstype> {
    IPage pageCC(IPage<Goodstype> page, Wrapper wrapper);
}
