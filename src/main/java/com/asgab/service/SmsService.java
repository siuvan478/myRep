package com.asgab.service;

import com.asgab.HttpClientUtil;
import com.google.common.collect.Maps;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;

/**
 * 短信发送工具类
 */
@Component
public class SmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsService.class);

    @Value("${sms.send_vercode_url}")
    private String send_vercode_url;

    @Value("${sms.accountno}")
    private String accountno;

    @Value("${sms.user}")
    private String user;

    @Value("${sms.pwd}")
    private String pwd;

    public boolean sendVerifyCode(String phone, String verifyCode) {
        boolean success = false;
        Map<String, String> param = Maps.newHashMap();
        param.put("accountno", accountno);
        param.put("user", user);
        param.put("pwd", pwd);
        param.put("phone", phone);
        param.put("msg", "Verification code is:" + verifyCode);
        Map<String, String> result = Maps.newHashMap();
        try {
            String xml = HttpClientUtil.doGet(send_vercode_url, param).trim().replace("\uFEFF", "");
            SAXReader reader = new SAXReader();
            Document document = reader.read(new StringReader(xml));
            Element bookStore = document.getRootElement();
            Iterator it = bookStore.elementIterator();
            while (it.hasNext()) {
                Element book = (Element) it.next();
                Iterator itt = book.elementIterator();
                while (itt.hasNext()) {
                    Element bookChild = (Element) itt.next();
                    result.put(bookChild.getName(), bookChild.getStringValue());
                }
            }

            if (result.get("msg_status") != null && result.get("msg_status").equals("100")) {
                LOGGER.debug("SMS send successfully, param-->" + param.toString());
                success = true;
            } else {
                LOGGER.error("SMS send failure, param-->" + param.toString());
                LOGGER.error("SMS send failure, result-->" + result.toString());
            }
        } catch (Exception e) {
            LOGGER.error("SMS send failure, param-->" + param.toString());
            LOGGER.error("SMS send failure, result-->" + result.toString());
        }

        return success;
    }

}