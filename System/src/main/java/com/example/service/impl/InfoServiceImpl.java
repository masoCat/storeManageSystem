package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Goods;
import com.example.entity.Info;
import com.example.mapper.GoodsMapper;
import com.example.mapper.InfoMapper;
import com.example.service.IInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * InnoDB free: 3072 kB 服务实现类
 * </p>
 *
 * @author maso
 * @since 2024-06-26
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements IInfoService {
    @Resource
    private InfoMapper infoMapper;

    @Override
    public IPage pageCC(IPage<Info> page, Wrapper wrapper) {
        return infoMapper.pageCC(page, wrapper);
    }
}
