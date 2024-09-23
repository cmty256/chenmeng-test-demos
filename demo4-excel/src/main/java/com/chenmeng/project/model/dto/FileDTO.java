package com.chenmeng.project.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author chenmeng
 */
@Data
public class FileDTO {

    @NotNull(message = "文件不能为空")
    private MultipartFile file;
}