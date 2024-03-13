package com.chenmeng.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenmeng.common.constants.enums.ErrorCodeEnum;
import com.chenmeng.common.exception.BusinessException;
import com.chenmeng.common.mapper.UserMapper;
import com.chenmeng.common.model.entity.User;
import com.chenmeng.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.chenmeng.common.constants.UserConstants.USER_LOGIN_STATE;

/**
 * @author 沉梦听雨
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 1. 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }
        // 2. 从数据库查询
        // todo 追求性能的话可以注释，直接走缓存
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }
}




