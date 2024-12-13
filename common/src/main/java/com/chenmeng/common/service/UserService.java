package com.chenmeng.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenmeng.common.model.entity.UserDO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenmeng
 */
public interface UserService extends IService<UserDO> {

    /**
     * 获取当前登录用户
     *
     * @param request 请求
     * @return 用户信息
     */
    UserDO getLoginUser(HttpServletRequest request);
}
