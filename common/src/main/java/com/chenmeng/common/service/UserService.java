package com.chenmeng.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenmeng.common.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cmty256
 */
public interface UserService extends IService<User> {

    /**
     * 获取当前登录用户
     *
     * @param request 请求
     * @return 用户信息
     */
    User getLoginUser(HttpServletRequest request);
}
