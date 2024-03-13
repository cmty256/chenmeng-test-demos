package com.chenmeng.common.model.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * 删除请求体
 *
 * @author 沉梦听雨
 */
@Data
public class DeleteDTO implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}