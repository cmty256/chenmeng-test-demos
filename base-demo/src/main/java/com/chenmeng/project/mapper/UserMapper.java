package com.chenmeng.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenmeng.project.model.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 沉梦听雨
*/
public interface UserMapper extends BaseMapper<User> {

    Page<User> findAll(Page<User> page);

    void batchInsert(@Param("userList") List<User> userList);
}




