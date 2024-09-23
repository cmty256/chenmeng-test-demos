package com.chenmeng.project.excel.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息，模型。
 */
@Data
public class UserInfoModel3 implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户姓名。
     */
    private String userName;

    /**
     * 用户性别。
     */
    private Integer userGender;

    /**
     * 用户生日。
     */
    private Date userBirth;

}
