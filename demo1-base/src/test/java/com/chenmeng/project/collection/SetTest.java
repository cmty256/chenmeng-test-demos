package com.chenmeng.project.collection;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenmeng
 */
@SpringBootTest
public class SetTest {

    /**
     * 测试 retainAll 方法：计算两个集合的交集
     */
    @Test
    public void testRetainAll() {
        // 给定数据
        Set<Long> channelIds = new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L));
        Set<Long> requestChannelIds = new HashSet<>(Arrays.asList(3L, 4L, 5L, 6L));

        // 创建副本以避免修改原始集合
        Set<Long> intersection = new HashSet<>(channelIds);

        // 执行操作：计算两个集合的交集
        boolean changed = intersection.retainAll(requestChannelIds);

        // 验证结果
        assertTrue(changed, "The retainAll method should have modified the set.");
        assertEquals(2, intersection.size(), "The intersection size should be 2.");
        assertTrue(intersection.containsAll(Arrays.asList(3L, 4L)), "交集应该只包含3和4.");

        // 检查原始集合未被修改
        assertEquals(4, channelIds.size(), "原始的通道id设置不应该被修改.");
        assertNotEquals(channelIds, intersection, "原始集合应该不同于交集.");
    }
}
