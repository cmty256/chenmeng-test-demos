package com.chenmeng.common.model.dto;

import com.chenmeng.common.constants.CommonConstants;
import lombok.Data;


/**
 * 分页请求体
 *
 * @author 沉梦听雨
 */
@Data
public class BasePageDTO {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = CommonConstants.SORT_ORDER_ASC;
}
