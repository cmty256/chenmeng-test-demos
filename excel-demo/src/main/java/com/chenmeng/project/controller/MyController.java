package com.chenmeng.project.controller;

import com.chenmeng.project.service.TblExcelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 测试控制器
 *
 * @author cmty256
 * @date 2023/11/09 16:34
 **/
@RestController
@RequestMapping("/test")
public class MyController {

    @Resource
    private TblExcelService excelService;

    @PostMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        excelService.export(response);
    }

}
