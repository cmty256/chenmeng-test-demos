package com.chenmeng.project.service.impl;

import com.chenmeng.project.service.TblUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class TblUserServiceImplTest {

    @Resource
    private TblUserService userService;

    @Test
    void test1() {
        userService.testUpdate();
    }

}