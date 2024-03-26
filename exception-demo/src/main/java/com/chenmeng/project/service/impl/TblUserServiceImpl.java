package com.chenmeng.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenmeng.project.mapper.TblUserMapper;
import com.chenmeng.project.model.entity.TblUser;
import com.chenmeng.project.service.TblUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.MalformedInputException;

/**
 * @author cmty256
 * @description 针对表【tbl_user】的数据库操作Service实现
 * @createDate 2023-12-01 23:59:11
 */
@Service
@Slf4j
public class TblUserServiceImpl extends ServiceImpl<TblUserMapper, TblUser>
        implements TblUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testUpdate() {
        try {
            TblUser user = new TblUser();
            user.setId(1L);
            user.setName("张三");
            this.updateById(user);
            test2();
        } catch (Exception e) {
            log.error("111");
            // 如果catch了，就需要抛出异常，否则触发不了事务回滚
            throw new RuntimeException(e);
        }
    }

    private void test2() {
        TblUser user = new TblUser();
        user.setId(1L);
        user.setName("张三222");
        this.updateById(user);
        throw new NullPointerException();
        // try {
        //     test3();
        //     log.info("info");
        // } catch (Exception e) {
        //     log.error("error");
        //     需要向上抛到顶层，才可触发事务回滚
        //     throw new RuntimeException(e);
        // }
    }

    private void test3() throws MalformedInputException {
        TblUser user = new TblUser();
        user.setId(1L);
        user.setName("张三333");
        this.updateById(user);
        throw new MalformedInputException(3);
    }
}
