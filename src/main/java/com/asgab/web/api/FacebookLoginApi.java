package com.asgab.web.api;

import com.asgab.HttpClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/10/29 0029.
 */
@Controller
@RequestMapping("/api/facebook")
public class FacebookLoginApi {

    //应用编号
    private static String client_id = "";
    //应用秘钥
    private static String client_secret = "";
    //获取用户信息
    private static String user_url = "https://graph.facebook.com/me";
    //验证口令
    private static String verify_token_url = "https://graph.facebook.com/debug_token";
    //获取应用口令
    private static String get_app_access_token_url = "https://graph.facebook.com/v2.8/oauth/access_token";

    /**
     * @return String
     * @throws
     * @Title: verifyToken
     * @Description: 调用图谱API，验证口令  app_id 和 user_id 字段将帮助您的应用确认访问口令对用户和您的应用有效。
     * @author <span style="color:#FF0000;">第五步验证访问的用户是否来自你的应用，防刷功能，防止恶意注册  </span>
     * @date Mar 17, 2017 9:50:38 AM
     */
    @RequestMapping("/accessTokenVerify")
    @ResponseBody
    public Object verifyToken(String accessToken) {
        HashMap<String, String> params = new HashMap<>();
        params.put("input_token", accessToken);//校验口令
        params.put("access_token", getAppToken());//应用口令
        String responseResult = null;
        String data = null;
        try {
            responseResult = HttpClientUtil.doGet(verify_token_url, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (null != responseResult && responseResult[0].equals("200")) {
//            String result = responseResult[1];
//            JSONObject jsonObject = JSONObject.fromObject(result);
//            data = jsonObject.getString("data");
//            System.out.println(data);
//        }
//      {
//          "data": {
//              "app_id": 138483919580948,
//              "application": "Social Cafe",
//              "expires_at": 1352419328,
//              "is_valid": true,
//              "issued_at": 1347235328,
//              "metadata": {
//                  "sso": "iphone-safari"
//              },
//              "scopes": [
//                  "email",
//                  "publish_actions"
//              ],
//              "user_id": 1207059
//          }
//      }
//        {
//            "data": {
//            "app_id": 000000000000000, "expires_at": 1352419328,
//                    "is_valid": true,
//                    "issued_at": 1347235328,
//                    "scopes": [
//            "email",
//                    "publish_actions"
//        ],
//            "user_id": 100207059
//        }
//        }
        return data;
    }


    /**
     * @return String
     * @throws
     * @Title: getAppToken
     * @Description: 获取应用口令（用来验证口令是否来自我的应用）
     * @author gaona
     * @date Mar 20, 2017 3:16:26 PM
     */
    public String getAppToken() {
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", client_id);
        params.put("client_secret", client_secret);
        params.put("grant_type", "client_credentials");
        String responseResult = null;
        String appToken = null;
        try {
            responseResult = HttpClientUtil.doGet(get_app_access_token_url, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (null != responseResult && responseResult[0].equals("200")) {
//            String result = responseResult[1];
//            JSONObject jsonObject = JSONObject.parseObject(result);
//            appToken = jsonObject.getString("access_token");
//            System.out.println(appToken);
//        }
        return appToken;
    }
}
