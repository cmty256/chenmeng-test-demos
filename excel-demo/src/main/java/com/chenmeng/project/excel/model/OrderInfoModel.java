package com.chenmeng.project.excel.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息，模型。
 */
@Data
public class OrderInfoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单标题。
     */
    private String orderTitle;

    /**
     * 订单价格。
     */
    private BigDecimal orderPrice;

    /**
     * 订单时间。
     */
    private Date orderTime;

}
