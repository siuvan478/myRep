package com.asgab.core;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Administrator on 2017/10/29 0029.
 */
public class JPush {

    private static final Logger LOGGER = LoggerFactory.getLogger(JPush.class);

    /**
     * JPush AppKey
     */
    private static final String appKey = "af2c6c809848bfdea4130b07";
    /**
     * JPush AppSecret
     */
    private static final String masterSecret = "341654df09d30c6c463395a5";

    /**
     * 推送app消息
     *
     * @param alias   设备别名
     * @param title   消息标题
     * @param content 消息正文
     */
    public static void pushMessageToApp(String alias, String title, String content) {
        JPushClient pushClient = new JPushClient(masterSecret, appKey);
        PushPayload pushPayload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title).build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtra(title, content).build())
                        .build())
                .build();
        try {
            PushResult pushResult = pushClient.sendPush(pushPayload.toString());
            LOGGER.info("schedule result is " + pushResult);
        } catch (APIConnectionException e) {
            LOGGER.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOGGER.error("Error response from JPush server. Should review and fix it. ", e);
            LOGGER.info("HTTP Status: " + e.getStatus());
            LOGGER.info("Error Code: " + e.getErrorCode());
            LOGGER.info("Error Message: " + e.getErrorMessage());
        }
    }


    public static void main(String[] args) {
        String alias = "470508081@qq.com";
        String title = "test jPush message";
        String content = "hello 470508081@qq.com, this is testing message";
        pushMessageToApp(alias, title, content);
    }
}