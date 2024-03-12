package com.chenmeng.project.service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.chenmeng.project.config.JiGuangConfig;
import com.chenmeng.project.dto.PushDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @email: pengyujun53@163.com
 * @author: peng_YuJun
 * @date: 2022/12/27
 * @time: 9:25
 */
@Slf4j
@Service
public class JiGuangPushService {
    /** 一次推送最大数量 (极光限制1000) */
    private static final int max_size = 800;

    @Autowired
    private JiGuangConfig jPushConfig;


    /**
     * 广播 (所有平台，所有设备, 不支持附加信息)
     * @return
     */
    public boolean pushAll(PushDTO pushBean){
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(pushBean.getAlert()))
                .build());
    }

    /**
     * 推送全部ios ios广播
     * @return
     */
    public boolean pushIos(PushDTO pushBean){
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.ios(pushBean.getAlert(), pushBean.getExtras()))
                .build());
    }

    /**
     * 推送ios 指定id
     * @return
     */
    public boolean pushIos(PushDTO pushBean, String... registids){
        // 剔除无效registed
        registids = checkRegistids(registids);
        // 每次推送max_size个
        while (registids.length > max_size) {
            sendPush(PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(Audience.registrationId(Arrays.copyOfRange(registids, 0, max_size)))
                    .setNotification(Notification.ios(pushBean.getAlert(), pushBean.getExtras()))
                    .build());
            registids = Arrays.copyOfRange(registids, max_size, registids.length);
        }
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(Arrays.copyOfRange(registids, 0, max_size)))
                .setNotification(Notification.ios(pushBean.getAlert(), pushBean.getExtras()))
                .build());
    }

    /**
     * 推送全部android
     * @return
     */
    public boolean pushAndroid(PushDTO pushBean){
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.android(pushBean.getAlert(), pushBean.getTitle(), pushBean.getExtras()))
                .build());
    }

    /**
     * 推送android 指定id
     * @return
     */
    public boolean pushAndroid(PushDTO pushBean, String... registids){
        // 剔除无效registed
        registids = checkRegistids(registids);
        // 每次推送max_size个
        while (registids.length > max_size) {
            sendPush(PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.registrationId(Arrays.copyOfRange(registids, 0, max_size)))
                    .setNotification(Notification.android(pushBean.getAlert(), pushBean.getTitle(), pushBean.getExtras()))
                    .build());
            registids = Arrays.copyOfRange(registids, max_size, registids.length);
        }
        return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registids))
                .setNotification(Notification.android(pushBean.getAlert(), pushBean.getTitle(), pushBean.getExtras()))
                .build());
    }

    /**
     * 剔除无效registed
     * @param registids
     * @return
     */
    public String[] checkRegistids(String[] registids) {
        List<String> regList = new ArrayList<String>(registids.length);
        for (String registid : registids) {
            if (registid!=null && !"".equals(registid.trim())) {
                regList.add(registid);
            }
        }
        return regList.toArray(new String[0]);
    }

    /**
     * 调用api推送
     * @param pushPayload 推送实体
     * @return
     */
    public boolean sendPush(PushPayload pushPayload){
        PushResult result = null;
        try{
            result = jPushConfig.getJPushClient().sendPush(pushPayload);
        } catch (APIConnectionException e) {
            log.error("极光推送连接异常: ", e);
        } catch (APIRequestException e) {
            log.error("极光推送请求异常: ", e);
        }
        if (result!=null && result.isResultOK()) {
            log.info("极光推送请求成功: {}", result);
            return true;
        }else {
            log.info("极光推送请求失败: {}", result);
            return false;
        }
    }
}
