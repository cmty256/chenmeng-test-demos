package com.chenmeng.project.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.chenmeng.project.convert.AlertLevelConvert;
import lombok.Data;

import java.net.URL;

/**
 * 告警消息导出VO
 *
 * @author chenmeng
 * @date 2023/11/10 11:43
 **/
@Data
@HeadRowHeight(30)
@ContentRowHeight(100)
@ColumnWidth(20)
public class AlarmExportVO {

    /**
     * 企业所属镇街
     */
    @ExcelProperty(value = "企业所属镇街", index = 0)
    private String enterpriseTown;

    /**
     * 企业所属市场监督所
     */
    @ExcelProperty(value = "企业所属市场监督所", index = 1)
    private String marketSupervision;

    /**
     * 设备名称
     */
    @ExcelProperty(value = "设备名称", index = 2)
    @ColumnWidth(30)
    private String deviceName;

    /**
     * 告警类型
     */
    @ExcelProperty(value = "告警类型", index = 3)
    private String alarmType;

    /**
     * 告警级别
     */
    @ExcelProperty(value = "告警级别", index = 4, converter = AlertLevelConvert.class)
    private String alarmLevel;

    /**
     * 告警时间
     */
    @ExcelProperty(value = "告警时间", index = 5)
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(25)
    private String alarmTime;

    /**
     * 告警图片路径
     */
    @ExcelProperty(value = "告警图片路径", index = 6)
    @ColumnWidth(45)
    private URL alarmImage;

}
