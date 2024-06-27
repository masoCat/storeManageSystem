package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Storage;
import com.example.mapper.StorageMapper;
import com.example.service.IStorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * InnoDB free: 3072 kB 服务实现类
 * </p>
 *
 * @author maso
 * @since 2024-06-19
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements IStorageService {

    @Resource
    private StorageMapper storageMapper;

    @Override
    public IPage pageCC(IPage<Storage> page, Wrapper wrapper) {
        return storageMapper.pageCC(page, wrapper);
    }

}
