package com.chenmeng.project;
import java.util.Date;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chenmeng.project.mapper.AlarmMapper;
import com.chenmeng.project.model.dto.AlarmDTO;
import com.chenmeng.project.model.entity.Alarm;
import com.chenmeng.project.convert.BasicConvert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ExcelDemoApplicationTests {

    @Resource
    private AlarmMapper alarmMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        AlarmDTO reqDTO = new AlarmDTO();
        reqDTO.setId(0);
        reqDTO.setEnterpriseTown("");
        reqDTO.setMarketSupervision("");
        reqDTO.setDeviceName("");
        reqDTO.setAlarmType("");
        reqDTO.setAlarmLevel("");
        reqDTO.setAlarmTime(new Date());
        reqDTO.setAlarmImage("");

        IPage<Alarm> page = alarmMapper.getPageInfo(BasicConvert.convertPage(reqDTO), reqDTO);
        System.out.println(page.getRecords().size());
    }

}
