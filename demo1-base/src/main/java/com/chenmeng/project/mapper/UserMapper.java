package com.chenmeng.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenmeng.common.model.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author chenmeng
*/
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    Page<UserDO> findAll(Page<UserDO> page);

    void batchInsert(@Param("userList") List<UserDO> userList);
}




