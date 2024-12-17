package com.chenmeng.project.db;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenmeng.common.constants.enums.SexEnum;
import com.chenmeng.common.model.entity.UserDO;
import com.chenmeng.project.mapper.UserMapper;
import com.chenmeng.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author chenmeng
 **/
@Slf4j
@SpringBootTest
class SaveDataTest {

    @Resource
    private UserService userService;

    /**
     * 批量插入数据（user表）
     */
    @Test
    void saveData() {
        LinkedList<UserDO> list = new LinkedList<>();
        for (int i = 0; i < 999; i++) {
            UserDO userDO = new UserDO();
            userDO.setUserAccount(RandomUtil.randomString(8));
            userDO.setUserPassword("12345678");
            userDO.setUserName("张三");
            userDO.setUserAvatar("头像");
            userDO.setGender(1);
            userDO.setPhone("13112345678");
            userDO.setEmail("邮箱");
            userDO.setUserProfile("张三在信息技术领域拥有超过十年的经验，尤其擅长软件开发和系统架构设计。他精通多种编程语言和技术栈，包括Java、Python、JavaScript等，并且在云计算、大数据处理和人工智能方面也有深入的研究。张三曾主导多个大型项目的研发工作，成功为多家企业提供了解决方案，帮助企业提升了效率和竞争力。例如，在某知名互联网公司任职期间，他带领团队完成了关键业务系统的重构，不仅提高了系统的稳定性和性能，还显著降低了运维成本。除了作为技术人员，张三也是一位成功的创业者。2035年，他创立了自己的科技公司——“智联未来”，致力于通过技术创新改善人们的生活方式。公司专注于智能家居产品的研发与销售，推出了一系列广受市场欢迎的产品，如智能音箱、智能门锁等。凭借敏锐的市场洞察力和扎实的技术功底，张三带领团队迅速占领了市场份额，使公司在短短几年内成为行业内的佼佼者。“智联未来”也因此获得了多项荣誉和奖项，成为行业内公认的标杆企业。工作之余，张三热爱旅行和摄影，足迹遍布世界各地。他认为，旅行不仅可以开阔眼界，更能让人学会从不同的角度看待世界。每到一个地方，张三都会用镜头记录下那些美好的瞬间，分享给身边的朋友们。他还喜欢阅读和写作，经常在博客上发表文章，探讨技术趋势、创业心得以及生活感悟。张三相信，持续学习是保持竞争力的关键，因此他始终保持一颗好奇心，不断探索未知领域。");
            userDO.setUserRole("user");
            list.add(userDO);
            if (i % 1000 == 0) {
                userService.saveBatch(list);
                list.clear();
            }
        }
        if (!list.isEmpty()) {
            userService.saveBatch(list);
        }
        System.out.println("userService.count() = " + userService.count());
    }
}