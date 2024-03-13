package com.chenmeng.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenmeng.project.model.entity.User;
import com.chenmeng.project.mapper.UserMapper;
import com.chenmeng.project.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author 沉梦听雨
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




