package com.chenmeng.project.vo;

import lombok.Data;

/**
 * @author chenmeng
 */
@Data
public class UserImportExcelVO {

    /**
     * 登录账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 性别：0 - 未知；1 - 男；2 -女
     */
    private Integer gender;

    /**
     * 用户角色：user - 普通用户；admin - 管理员
     */
    private String userRole;
}
