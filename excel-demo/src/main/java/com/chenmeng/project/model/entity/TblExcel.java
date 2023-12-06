package com.chenmeng.project.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Excel测试表
 *
 * @author 沉梦听雨
 * @TableName tbl_excel
 */
@Data
@TableName(value ="tbl_excel")
@AllArgsConstructor
public class TblExcel implements Serializable {
    /**
     * 名字
     */
    @ExcelProperty(value = "名字")
    private String name;

    /**
     * 文件
     */
    @ExcelProperty(value = "图片url")
    private String file;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}