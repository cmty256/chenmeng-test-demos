package com.chenmeng.project.course.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.chenmeng.project.course.converter.UserInfoGenderConverter;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户信息，模型。
 */
@Data
public class UserInfoModel4 implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户姓名。
     */
    @ExcelProperty(value = {"基本信息", "用户姓名"})
    private String userName;

    /**
     * 用户性别。
     */
    @ExcelProperty(value = {"基本信息", "用户性别"}, converter = UserInfoGenderConverter.class)
    private Integer userGender;

    /**
     * 用户生日。
     */
    @ExcelProperty(value = {"基本信息", "用户生日"})
    @DateTimeFormat(value = "yyyy年MM月dd日")
    private String userBirth;

    /**
     * 用户积分。
     */
    @ExcelProperty(value = {"账户信息", "用户积分"})
    private Integer userScore;

    /**
     * 用户佣金。
     */
    @ExcelProperty(value = {"账户信息", "用户佣金"})
    @NumberFormat(value = "￥#.##")
    private BigDecimal userReward;

}
