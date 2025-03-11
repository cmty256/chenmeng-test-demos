package com.chenmeng.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenmeng.common.model.entity.UserDO;
import com.chenmeng.project.mapper.UserMapper;
import com.chenmeng.project.service.UserService;
import com.chenmeng.project.vo.UserImportExcelVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
* @author chenmeng
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO>
    implements UserService {

    private final DataSourceTransactionManager dataSourceTransactionManager;
    private final ThreadPoolTaskExecutor myExecutor;

    @Override
    @Transactional(rollbackFor = Exception.class) // 有无声明，数据都会回滚
    public Boolean importExcel(List<UserImportExcelVO> importList) {
        if (importList.isEmpty()) {
            throw new RuntimeException("导入数据有误");
        }

        List<UserDO> doList = BeanUtil.copyToList(importList, UserDO.class);
        int batchSize = 100;
        int totalSize = doList.size();
        final int[] count = {0};

        try {
            // 使用 CompletableFuture 处理异步任务
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (int i = 0; i < totalSize; i += batchSize) {
                int end = Math.min(i + batchSize, totalSize);
                List<UserDO> batch = doList.subList(i, end);
                CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                    // 手动开启事务
                    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                    // 事物隔离级别，开启新事务，这样会比较安全些。（表示：表示每次都开启一个新的事务）
                    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    // 获得事务状态
                    TransactionStatus status = dataSourceTransactionManager.getTransaction(def);
                    try {
                        saveBatch(batch);
                        count[0] += batch.size();
                        log.info("[importExcel][第({})条数据导入成功！]", count);
                    } catch (Exception e) {
                        // 事务回滚
                        dataSourceTransactionManager.rollback(status);
                        throw new RuntimeException(e);
                    }
                    return null;
                }, myExecutor);

                futures.add(future);
            }

            // 等待所有任务完成
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            log.error("[importExcel][导入数据异常: {}]", e.getMessage(), e);
            throw new RuntimeException("导入数据异常");
        }

        log.info("[importExcel][导入Excel数据，数量为({})]", count[0]);
        return true;
    }

}




