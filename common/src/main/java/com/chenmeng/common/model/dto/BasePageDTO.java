package com.chenmeng.common.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 基础分页DTO
 *
 * @author 沉梦听雨
 */
@Data
public class BasePageDTO implements Serializable {

    @NotNull(message = "【当前页码】不能为空")
    private Integer current;

    @NotNull(message = "【每页条数】不能为空")
    private Integer size;

    private static final long serialVersionUID = 8916337394266613506L;
}