package com.chenmeng.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenmeng.project.model.dto.AlarmDTO;
import com.chenmeng.project.model.entity.Alarm;

/**
* @author 沉梦听雨
* @description 针对表【alarm(告警消息表)】的数据库操作Mapper
* @createDate 2023-11-10 11:40:55
* @Entity generator.model.Alarm
*/
public interface AlarmMapper extends BaseMapper<Alarm> {

    IPage<Alarm> getPageInfo(Page page, AlarmDTO reqDTO);
}




