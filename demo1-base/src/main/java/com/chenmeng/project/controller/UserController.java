package com.chenmeng.project.controller;

import cn.hutool.core.bean.BeanUtil;
import com.chenmeng.project.model.entity.User;
import com.chenmeng.project.model.vo.UserVO;
import com.chenmeng.project.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户表控制器
 *
 * @author chenmeng
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 测试前端接收 Long 类型id，精度丢失问题
     * 可在视图类加上JsonSerialize这个注解来解决，单靠类实现Serializable接口不行（或者用String）
     *
     * @return List<UserVO>
     */
    @GetMapping
    public List<UserVO> testLong() {
        List<User> list = userService.list();
        System.out.println("list = " + list);

        List<UserVO> voList = BeanUtil.copyToList(list, UserVO.class);
        System.out.println("userVOS = " + voList);
        return voList;
    }
}
