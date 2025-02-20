package com.chenmeng.project.controller;

import com.chenmeng.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenmeng
 */
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(String username) {
        userService.register(username);
        return "success";
    }
}
