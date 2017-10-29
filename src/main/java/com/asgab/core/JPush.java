package com.asgab.core;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleClient;
import cn.jpush.api.schedule.ScheduleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Administrator on 2017/10/29 0029.
 */
public class JPush {

    private static final Logger LOGGER = LoggerFactory.getLogger(JPush.class);

    public static void main(String[] args) {
        String masterSecret = "341654df09d30c6c463395a5";
        String appKey = "af2c6c809848bfdea4130b07";
        ScheduleClient scheduleClient = new ScheduleClient(masterSecret, appKey);
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        String name = "test_schedule_example";
        String time = "2017-10-29 16:23:48";
      // (new PushPayload.Builder()).setPlatform(Platform.all()).setAudience(Audience.registrationId("")).setNotification(Notification.alert(alert)).build();
        PushPayload push = PushPayload.alertAll("test schedule example.");
        try {
            ScheduleResult result = jpushClient.createSingleSchedule(name, time, push);
            LOGGER.info("schedule result is " + result);
        } catch (APIConnectionException e) {
            LOGGER.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOGGER.error("Error response from JPush server. Should review and fix it. ", e);
            LOGGER.info("HTTP Status: " + e.getStatus());
            LOGGER.info("Error Code: " + e.getErrorCode());
            LOGGER.info("Error Message: " + e.getErrorMessage());
        }
    }
}
