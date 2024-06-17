package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;

/**
 * <p>
 * InnoDB free: 3072 kB 服务类
 * </p>
 *
 * @author maso
 * @since 2024-06-12
 */

public interface IUserService extends IService<User> {

    IPage pageC(IPage<User> page);

    IPage pageCC(IPage<User> page, Wrapper wrapper);
}
