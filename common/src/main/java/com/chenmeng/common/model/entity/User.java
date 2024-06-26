package com.chenmeng.common.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表实体类
 *
 * @author chenmeng
 */
@Data
@TableName(value ="user")
public class User implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 登录账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别：0 - 未知；1 - 男；2 -女
     */
    private Integer gender;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 用户角色：user - 普通用户；admin - 管理员
     */
    private String userRole;

    /**
     * 用户状态：0 - 正常；1 - 禁用
     */
    private String userStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除：0 - 未删；1 - 已删
     * <p>
     * 注解：逻辑删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}