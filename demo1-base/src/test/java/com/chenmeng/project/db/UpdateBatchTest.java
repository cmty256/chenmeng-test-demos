package com.chenmeng.project.db;

import cn.hutool.core.util.RandomUtil;
import com.chenmeng.common.model.entity.UserDO;
import com.chenmeng.project.mapper.UserMapper;
import com.chenmeng.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenmeng
 **/
@Slf4j
@SpringBootTest
class UpdateBatchTest {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    /**
     * 批量更新性能测试
     * 自定义的批量更新，性能更高
     */
    @Test
    void testUpdateBatch() {
        List<UserDO> list = new ArrayList<>();
        for (int i = 804182; i < 804182 + 400; i++) {
            UserDO userDO = new UserDO();
            userDO.setId((long) i);
            userDO.setUserAccount(RandomUtil.randomString(8));
            userDO.setUserPassword("12345678");
            userDO.setUserName("张三" + i);
            userDO.setUserAvatar("头像");
            userDO.setGender(1);
            userDO.setPhone("13112345678");
            userDO.setEmail("邮箱");
            list.add(userDO);
        }
        // 预热
        userService.updateBatchById(list);

        // rewriteBatchedStatements=true 不生效
        long startTime = System.currentTimeMillis();
        userService.updateBatchById(list);
        long endTime = System.currentTimeMillis();
        log.info("MyBatis-Plus 的批量更新，耗时：{}ms", endTime - startTime);

        // 需配置 allowMultiQueries=true，否则报错
        long startTime2 = System.currentTimeMillis();
        userMapper.batchUpdate(list);
        long endTime2 = System.currentTimeMillis();
        log.info("自定义的批量更新，耗时：{}ms", endTime2 - startTime2);
    }
}