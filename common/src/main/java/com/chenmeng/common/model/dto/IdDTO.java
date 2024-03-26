package com.chenmeng.common.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * idDTO
 *
 * @author 沉梦听雨
 */
@Data
public class IdDTO implements Serializable {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    private static final long serialVersionUID = 1L;
}