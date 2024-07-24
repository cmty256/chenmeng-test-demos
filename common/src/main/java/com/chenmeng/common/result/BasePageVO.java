package com.chenmeng.common.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通用分页返回结果类
 *
 * @author chenmeng
 */
@Data
public class BasePageVO<T> implements Serializable {

    /**
     * 当前页
     */
    private Integer current;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 当前页的列表
     */
    private List<T> list;
}