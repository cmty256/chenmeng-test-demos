package com.chenmeng.project.controller;

import com.chenmeng.project.model.dto.FileDTO;
import com.chenmeng.project.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 测试控制器
 *
 * @author chenmeng
 * @date 2023/11/09 16:34
 **/
@RestController
@RequestMapping("/test")
public class MyController {

    @Resource
    private ExcelService excelService;

    @PostMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        excelService.export(response);
    }

    @PostMapping("/import")
    @Operation(summary = "导入企业信息")
    public boolean importInfo(@Valid FileDTO dto) {
        return excelService.importInfo(dto);
    }
}
