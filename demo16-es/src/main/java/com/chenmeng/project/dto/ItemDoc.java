package com.chenmeng.project.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 索引库实体
 *
 * @author chenmeng
 */
@Data
public class ItemDoc {

    /**
     * 商品id
     */
    private String id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 价格（分）
     */
    private Integer price;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 类目名称
     */
    private String category;

    /**
     * 品牌名称
     */
    private String brand;

    /**
     * 销量
     */
    private Integer sold;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 是否是推广广告，true/false
     */
    private Boolean isAD;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}