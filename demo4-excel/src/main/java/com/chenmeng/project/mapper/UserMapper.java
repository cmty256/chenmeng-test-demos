package com.chenmeng.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenmeng.common.model.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenmeng
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
