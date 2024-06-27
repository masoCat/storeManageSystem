package com.example.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * InnoDB free: 3072 kB Mapper 接口
 * </p>
 *
 * @author maso
 * @since 2024-06-12
 */

public interface UserMapper extends BaseMapper<User> {

    IPage pageC(IPage<User> page);

    IPage pageCC(IPage<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
