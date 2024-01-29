package com.chenmeng.project.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenmeng.project.mapper.UserMapper;
import com.chenmeng.project.model.entity.User;
import com.chenmeng.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 沉梦听雨
 **/
@Slf4j
@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Test
    void testFindAll() {
        log.info("==============(-1)查询所有==================");
        Page<User> page = new Page<>(1, -1);
        // SELECT  id,name,age,sex,is_delete  FROM tbl_user WHERE  is_delete=0
        Page<User> path = userService.page(page);
        log.info("path = " + path.getTotal()); // 0
        log.info("{}", path.getRecords());

        log.info("==============(-1)Mybatis查询所有==================");
        Page<User> page1 = new Page<>(1, -1);
        // 手写的sql：SELECT * FROM tbl_user
        Page<User> page11 = userMapper.findAll(page1);
        log.info("page11 = " + page11.getTotal()); // 0
        log.info("{}", page11.getRecords());

        log.info("==============分页==================");
        Page<User> page2 = new Page<>(1, 2);
        // SELECT COUNT(*) AS total FROM tbl_user WHERE is_delete = 0
        // SELECT  id,name,age,sex,is_delete  FROM tbl_user WHERE  is_delete=0 LIMIT 2
        Page<User> all = userService.page(page2);
        log.info("all = " + all.getTotal());
        log.info("{}", all.getRecords());

        log.info("==============Mybatis分页==================");
        Page<User> page3 = new Page<>(1, 2);
        // SELECT COUNT(*) AS total FROM tbl_user
        // SELECT * FROM tbl_user LIMIT 2
        Page<User> page33 = userMapper.findAll(page3);
        log.info("page11 = " + page33.getTotal());
        log.info("{}", page33.getRecords());
    }

    @Test
    void test2() {
        // SELECT  id,name,age,sex,is_delete  FROM tbl_user WHERE  is_delete=0
        userService.list();
    }
}