package com.chenmeng.project.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.chenmeng.project.excel.converter.UserInfoGenderConverter;
import lombok.Data;

/**
 * 用户信息，模型。
 */
@Data
public class UserInfoModel {

    /**
     * 昵称。
     */
    @ExcelProperty(value = "昵称")
    private String userName;

    /**
     * 性别。
     */
    @ExcelProperty(value = "性别", converter = UserInfoGenderConverter.class)
    private Integer userGender;

    /**
     * 生日。
     */
    @ExcelProperty(value = "生日")
    @DateTimeFormat(value = "yyyy年MM月dd日")
    private String userBirth;

    /**
     * 邮箱。
     */
    @ExcelProperty(value = "邮箱")
    private String userEmail;

    /**
     * 积分。
     */
    @ExcelProperty(value = "积分")
    private Integer userScore;

    /**
     * 排名。
     */
    @ExcelProperty(value = "排名")
    @NumberFormat(value = "#.##%")
    private String userRank;
}
