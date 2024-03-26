package com.chenmeng.project.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenmeng.project.model.entity.User;
import com.chenmeng.project.service.UserService;
import com.chenmeng.common.constants.enums.SexEnum;
import com.chenmeng.project.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cmty256
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

    @Test
    void test3() {
        System.out.println("sexEnum.getValueByKey(-1) = " + SexEnum.getValueByKey(-1));
    }

    @Test
    void test4() {
        User user1 = new User();
        // IdWorker 是一个基于zookeeper和snowflake算法的分布式ID生成工具
        user1.setId(IdWorker.getId());
        user1.setName("1号");
        user1.setAge(0);
        user1.setSex(0);
        user1.setIsDelete(0);

        User user2 = new User();
        // user2.setId(2L);
        user2.setName("2号");
        user2.setAge(0);
        user2.setSex(0);
        user2.setIsDelete(0);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        boolean b = userService.saveBatch(users, 2);
        System.out.println("b = " + b);

        // userMapper.batchInsert(users);
    }
}