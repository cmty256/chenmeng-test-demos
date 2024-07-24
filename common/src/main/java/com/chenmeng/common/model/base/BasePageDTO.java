package com.chenmeng.common.model.base;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 基础分页DTO
 *
 * @author chenmeng
 */
@Data
@SuppressWarnings("all")
public class BasePageDTO implements Serializable {

    @NotNull(message = "【当前页码】不能为空")
    private Integer current = 1;

    @NotNull(message = "【每页条数】不能为空")
    private Integer size = 10;

    private static final long serialVersionUID = 8916337394266613506L;
}