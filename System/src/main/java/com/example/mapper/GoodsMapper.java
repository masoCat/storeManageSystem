package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.Goods;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * InnoDB free: 3072 kB Mapper 接口
 * </p>
 *
 * @author maso
 * @since 2024-06-20
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    IPage pageCC(IPage<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
