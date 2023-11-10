package com.chenmeng.project.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * Excel测试表
 *
 * @author wyy
 * @TableName tbl_excel
 */
@TableName(value ="tbl_excel")
@Data
public class TblExcel implements Serializable {
    /**
     * 名字
     */
    private String name;

    /**
     * 文件
     */
    private String file;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}