package com.chenmeng.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chenmeng.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 用户表实体类
 *
 * @author chenmeng
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
public class UserDO extends BaseDO {

    /**
     * 登录账号
     */
    @NotBlank(message = "登录账号不能为空")
    private String userAccount;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
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
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user - 普通用户；admin - 管理员
     */
    @NotBlank(message = "用户角色不能为空")
    private String userRole;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}