package com.chenmeng.project.message;

import lombok.Data;

/**
 * 示例 04 的 Message 消息
 *
 * @author chenmeng
 */
@Data
public class Demo04Message {

    public static final String TOPIC = "DEMO_04";

    /**
     * 编号
     */
    private Integer id;

    private String mark;
}
