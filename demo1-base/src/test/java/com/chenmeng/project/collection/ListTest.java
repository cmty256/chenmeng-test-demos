package com.chenmeng.project.collection;

import cn.hutool.core.collection.ListUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenmeng
 */
@SpringBootTest
public class ListTest {

    /**
     * 封装字符串为列表
     */
    @Test
    void test2() {
        String str = "156, 446, 344, 158";
        System.out.println("ListUtil.toList(str) = " + ListUtil.toList(str));

        System.out.println("ListUtil.toLinkedList = " + ListUtil.toLinkedList("156", "446", "344", "158"));
    }

    /**
     * 获取满足指定规则所有的元素的位置
     */
    @Test
    void testIndexOfAll() {
        List<String> list = ListUtil.toLinkedList("1", "2", "3", "4", "3", "2", "1");

        // [1, 5]
        int[] indexArray = ListUtil.indexOfAll(list, "2"::equals);
        Arrays.stream(indexArray).forEach(System.out::println);
    }

    /**
     * 分页
     */
    @Test
    void testPage() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // 页码：0，每页大小：2，数据：[a, b]
        List<String> page1 = ListUtil.page(0, 2, list);
        System.out.println("页码：0，每页大小：2，数据：" + page1);

        // 页码：1，每页大小：2，数据：[c]
        List<String> page2 = ListUtil.page(1, 2, list);
        System.out.println("页码：1，每页大小：2，数据：" + page2);
    }

    /**
     * 列表截取
     */
    @Test
    void testSub() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // 截取索引为1到2（不包含2）的数据：[b]
        List<String> subList = ListUtil.sub(list, 1, 2);
        System.out.println("截取索引为1到2（不包含2）的数据：" + subList);


        List<Integer> of = ListUtil.of(1, 2, 3, 4);

        // [3, 4]
        List<Integer> sub = ListUtil.sub(of, 2, 4);

        // 对子列表操作不影响原列表，原列表：[1, 2, 3, 4]
        sub.remove(0);
        System.out.println("对子列表操作不影响原列表，原列表：" + of);
    }

    /**
     * 分割列表（分组）
     */
    @Test
    void testPartition() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // [[a, b], [c]]
        List<List<String>> partition = ListUtil.partition(list, 2);
        System.out.println(partition);

    }
}
