package com.chenmeng.project.controller;

import com.chenmeng.common.model.base.BasePageDTO;
import com.chenmeng.project.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author chenmeng
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/export-repeat")
    public void exportByRepeat(@Valid BasePageDTO pageReqVO, HttpServletResponse response) throws IOException {
        userService.exportByRepeat(pageReqVO, response);
    }
}
