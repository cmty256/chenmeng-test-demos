package com.chenmeng.project.controller;

import com.chenmeng.project.service.TblUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试控制器
 *
 * @author 沉梦
 * @date 2023/12/02 00:38
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TblUserService userService;

    @GetMapping("/demo")
    public void test() {
        userService.testUpdate();
    }

}
