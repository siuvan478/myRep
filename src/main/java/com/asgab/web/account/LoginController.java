package com.asgab.web.account;


import com.asgab.service.account.ShiroDbRealm.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * <p>
 * 真正登录的POST请求由Filter完成,
 *
 * @author calvin
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {


    @RequestMapping(method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            if (null != (ShiroUser) currentUser.getPrincipal()) {//如果已经登录，则退出or 跳转到主页
                currentUser.logout();    //安全起见，用这种，退出再登录
                //return "redirect:/";	//"/" 会根据spring-mvc.xml 配置通过 pageController 重新分配跳转到主页
            }
        }
        return "account/login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
        return "account/login";
    }


}
