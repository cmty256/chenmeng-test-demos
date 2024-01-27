package com.chenmeng.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenmeng.project.model.entity.User;

/**
* @author 沉梦听雨
*/
public interface UserMapper extends BaseMapper<User> {

    Page<User> findAll(Page<User> page);
}




