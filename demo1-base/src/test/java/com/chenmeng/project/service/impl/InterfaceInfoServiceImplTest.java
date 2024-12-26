package com.chenmeng.project.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenmeng.common.model.entity.InterfaceInfoDO;
import com.chenmeng.project.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author chenmeng
 **/
@Slf4j
@SpringBootTest
class InterfaceInfoServiceImplTest {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Test
    void testGetPage() {
        long begin = System.currentTimeMillis();
        Page<InterfaceInfoDO> objectPage = new Page<>(1, 10);
        Page<InterfaceInfoDO> page = interfaceInfoService.page(objectPage, Wrappers.<InterfaceInfoDO>lambdaQuery()
                .select(InterfaceInfoDO::getId, InterfaceInfoDO::getName));
        System.out.println("耗时：" + (System.currentTimeMillis() - begin));
        System.out.println(page.getCurrent());
    }
}