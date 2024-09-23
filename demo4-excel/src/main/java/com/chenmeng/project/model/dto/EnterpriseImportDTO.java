package com.chenmeng.project.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author chenmeng
 */
@Data
public class EnterpriseImportDTO {

    /**
     * 企业名称
     */
    @ExcelProperty("主体名称")
    private String entName;

    /**
     * 统一社会信用代码
     */
    @ExcelProperty("统一社会信用代码")
    private String registerCode;

    /**
     * 法定代表人
     */
    @ExcelProperty("法定代表人")
    private String legalPerson;

    /**
     * 联系电话
     */
    @ExcelProperty("联系电话")
    private String phone;
}
