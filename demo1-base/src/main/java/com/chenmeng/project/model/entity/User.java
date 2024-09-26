package com.chenmeng.project.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author chenmeng
 * @TableName tbl_user
 */
@TableName(value ="tbl_user")
@Data
public class User implements Serializable {
    /**
     * 标识(雪花算法)
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名字
     */
    @JsonProperty("userName")
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别：0=男，1=女
     */
    private Integer sex;

    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}