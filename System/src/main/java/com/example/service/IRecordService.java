package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Record;
import com.example.entity.Storage;

/**
 * <p>
 * InnoDB free: 3072 kB 服务类
 * </p>
 *
 * @author maso
 * @since 2024-06-21
 */
public interface IRecordService extends IService<Record> {
    IPage pageCC(IPage<Record> page, Wrapper wrapper);
}
