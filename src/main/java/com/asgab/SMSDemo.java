package com.asgab;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年10月16日 下午 9:13
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
public class SMSDemo {
//https://api.accessyou.com/sms/sendsms-vercode.php?
    public static void main(String[] args) {
        Map<String, String> param = new HashMap<>();
        param.put("accountno","11008714");
        param.put("pwd","61344562");
        HttpClientUtil.doGet("http://api.accessyou.com/sms/sendsms-vercode.php", param);
    }
}