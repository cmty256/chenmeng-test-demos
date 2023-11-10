package com.chenmeng.project.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.net.URL;

/**
 * Excel导出视图
 *
 * @author wyy
 * @date 2023/11/09 16:38
 **/
@Data
@HeadRowHeight(30)
@ContentRowHeight(100)
@ColumnWidth(20)
public class ExcelExportVO implements Serializable {

    @ExcelProperty(value = "名字")
    private String name;

    @ExcelProperty(value = "图片")
    @ColumnWidth(45)
    private URL file;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
