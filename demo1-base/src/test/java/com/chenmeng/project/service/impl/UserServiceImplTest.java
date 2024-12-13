package com.chenmeng.project.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenmeng.common.model.entity.UserDO;
import com.chenmeng.project.service.UserService;
import com.chenmeng.common.constants.enums.SexEnum;
import com.chenmeng.project.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author chenmeng
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
        Page<UserDO> page = new Page<>(1, -1);
        // SELECT  *  FROM user WHERE is_delete=0
        Page<UserDO>path = userService.page(page);
        log.info("path = " + path.getTotal()); // 0
        log.info("{}", path.getRecords());

        log.info("==============(-1)Mybatis查询所有==================");
        Page<UserDO>page1 = new Page<>(1, -1);
        // 手写的sql：SELECT * FROM user
        Page<UserDO>page11 = userMapper.findAll(page1);
        log.info("page11 = " + page11.getTotal()); // 0
        log.info("{}", page11.getRecords());

        log.info("==============分页==================");
        Page<UserDO>page2 = new Page<>(1, 2);
        // SELECT COUNT(*) AS total FROM user WHERE is_delete = 0
        // SELECT  *  FROM user WHERE  is_delete=0 LIMIT 2
        Page<UserDO>all = userService.page(page2);
        log.info("all = " + all.getTotal());
        log.info("{}", all.getRecords());

        log.info("==============Mybatis分页==================");
        Page<UserDO>page3 = new Page<>(1, 2);
        // SELECT COUNT(*) AS total FROM user
        // SELECT * FROM user LIMIT 2
        Page<UserDO>page33 = userMapper.findAll(page3);
        log.info("page11 = " + page33.getTotal());
        log.info("{}", page33.getRecords());
    }

    @Test
    void test2() {
        // SELECT * FROM user WHERE is_delete=0
        userService.list();
    }

    @Test
    void test3() {
        System.out.println("sexEnum.getValueByKey(-1) = " + SexEnum.getValueByKey(-1));
    }

    @Test
    void test4() {
        UserDO user1 = new UserDO();
        // IdWorker 是一个基于zookeeper和snowflake算法的分布式ID生成工具
        user1.setId(IdWorker.getId());
        user1.setUserName("1号");
        user1.setGender(0);
        user1.setIsDeleted(0);

        UserDO user2 = new UserDO();
        // user2.setId(2L);
        user2.setUserName("2号");
        user2.setGender(0);
        user2.setIsDeleted(0);

        List<UserDO>users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        boolean b = userService.saveBatch(users, 2);
        System.out.println("b = " + b);

        // userMapper.batchInsert(users);
    }

    /**
     * 批量插入数据
     */
    @Test
    void saveData() {
        LinkedList<UserDO> list = new LinkedList<>();
        for (int i = 0; i < 10_0000; i++) {
            UserDO userDO = new UserDO();
            userDO.setUserAccount(RandomUtil.randomString(8));
            userDO.setUserPassword("12345678");
            userDO.setUserName("张三");
            userDO.setUserAvatar("头像");
            userDO.setGender(1);
            userDO.setPhone("13112345678");
            userDO.setEmail("邮箱");
            userDO.setUserProfile("简介");
            userDO.setUserRole("user");
            list.add(userDO);
        }
        System.out.println("userService.saveBatch(list) = " + userService.saveBatch(list));
    }
}