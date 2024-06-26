package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.Info;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * InnoDB free: 3072 kB Mapper 接口
 * </p>
 *
 * @author maso
 * @since 2024-06-26
 */
public interface InfoMapper extends BaseMapper<Info> {
    IPage pageCC(IPage<Info> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
