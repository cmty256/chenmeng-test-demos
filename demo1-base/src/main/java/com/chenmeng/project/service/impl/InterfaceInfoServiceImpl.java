package com.chenmeng.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenmeng.common.model.entity.InterfaceInfoDO;
import com.chenmeng.project.mapper.InterfaceInfoMapper;
import com.chenmeng.project.service.InterfaceInfoService;
import org.springframework.stereotype.Service;

/**
 * @author chenmeng
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfoDO> implements InterfaceInfoService {
}
