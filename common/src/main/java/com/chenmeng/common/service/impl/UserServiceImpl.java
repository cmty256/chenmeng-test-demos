package com.chenmeng.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenmeng.common.exception.BusinessException;
import com.chenmeng.common.mapper.UserMapper;
import com.chenmeng.common.model.entity.UserDO;
import com.chenmeng.common.result.RespCodeEnum;
import com.chenmeng.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.chenmeng.common.constants.UserConstant.USER_LOGIN_STATE;

/**
 * @author chenmeng
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO>
        implements UserService {

    @Override
    public UserDO getLoginUser(HttpServletRequest request) {
        // 1. 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserDO currentUserDO = (UserDO) userObj;
        if (currentUserDO == null || currentUserDO.getId() == null) {
            throw new BusinessException(RespCodeEnum.NOT_LOGIN_ERROR);
        }
        // 2. 从数据库查询
        // todo 追求性能的话可以注释，直接走缓存
        long userId = currentUserDO.getId();
        currentUserDO = this.getById(userId);
        if (currentUserDO == null) {
            throw new BusinessException(RespCodeEnum.NOT_LOGIN_ERROR);
        }
        return currentUserDO;
    }
}




