package com.chenmeng.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chenmeng.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户信息表
 *
 * @author chenmeng
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "system_users")
@Data
public class SystemUsersDO extends BaseDO {

    /**
     * 用户账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 岗位编号数组
     */
    private String postIds;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;
}