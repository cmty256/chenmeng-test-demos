package com.chenmeng.project.message;

import lombok.Data;

/**
 * 示例 01 的 Message 消息
 *
 * @author chenmeng
 */
@Data
public class Demo01Message {

    public static final String TOPIC = "DEMO_01";

    /**
     * 编号
     */
    private Integer id;
}
