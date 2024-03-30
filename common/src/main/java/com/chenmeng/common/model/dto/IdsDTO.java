package com.chenmeng.common.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * id列表DTO
 *
 * @author chenmeng
 */
@Data
public class IdsDTO {

    @NotNull(message = "ids不能为空")
    @Size(min = 1, message = "ids至少要传一个id")
    private List<Long> ids;
}