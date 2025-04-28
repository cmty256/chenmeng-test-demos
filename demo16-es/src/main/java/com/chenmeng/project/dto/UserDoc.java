package com.chenmeng.project.dto;

import lombok.Data;

/**
 * @author chenmeng
 */
@Data
public class UserDoc {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 性别：0 - 未知；1 - 男；2 -女
     */
    private Integer gender;
}
