package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.Storage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * InnoDB free: 3072 kB Mapper 接口
 * </p>
 *
 * @author maso
 * @since 2024-06-19
 */
public interface StorageMapper extends BaseMapper<Storage> {
    IPage pageCC(IPage<Storage> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
