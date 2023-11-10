package com.chenmeng.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenmeng.project.mapper.AlarmMapper;
import com.chenmeng.project.model.entity.Alarm;
import com.chenmeng.project.service.AlarmService;
import org.springframework.stereotype.Service;

/**
* @author wyy
* @description 针对表【alarm(告警消息表)】的数据库操作Service实现
* @createDate 2023-11-10 11:40:55
*/
@Service
public class AlarmServiceImpl extends ServiceImpl<AlarmMapper, Alarm>
    implements AlarmService {

}




