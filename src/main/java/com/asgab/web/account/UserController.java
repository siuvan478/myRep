package com.asgab.web.account;

import com.asgab.core.pagination.Page;
import com.asgab.entity.User;
import com.asgab.service.account.AccountService;
import com.asgab.service.account.ShiroDbRealm;
import com.asgab.util.LoginUtil;
import com.asgab.web.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.security.auth.Subject;
import javax.servlet.ServletRequest;
import java.util.Map;

/*
* user管理的Controller, 使用Restful风格的Urls:
* 
* List page : GET /user/
* Create page : GET /user/create
* Create action : POST /user/create
* Update page : GET /user/update/{id}
* Update action : POST /user/update
* Delete action : GET /user/delete/{id}
* */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER) int pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                       @RequestParam(value = "sort", defaultValue = "") String sort, ServletRequest request, Model model) {
        //处理搜索条件
        Map<String, Object> params = dealSearchCondition(request, model, "loginName", "name", "email", "phone");
        params.put("sort", sort);
        Page<User> page = new Page<>(pageNumber, pageSize, sort, params);
        model.addAttribute("pages", accountService.getAllUser(page));
        ShiroDbRealm.ShiroUser userInfo = getCurrUserInfo();
        model.addAttribute("userId", userInfo == null ? "" : userInfo.id);
        return "account/usersList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String toCreate(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("action", "create");
        return "account/userForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(User user, RedirectAttributes redirectAttributes) {
        accountService.registerUser(user);
        redirectAttributes.addFlashAttribute("message", "create user success");
        return "redirect:/user";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", accountService.getUser(id));
        model.addAttribute("action", "update");
        return "account/userForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        accountService.updateUser(user);
        redirectAttributes.addFlashAttribute("message", "update user success");
        return "redirect:/user";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        accountService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "delete user success");
        return "redirect:/user";
    }

    @ModelAttribute
    public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("user", accountService.getUser(id));
        }
    }

    /**
     * Ajax请求校验loginName是否唯一。
     */
    @RequestMapping(value = "checkLoginName")
    @ResponseBody
    public String checkLoginName(@RequestParam("loginName") String loginName) {
        if (accountService.findUserByLoginName(loginName) == null) {
            return "true";
        } else {
            return "false";
        }
    }


}
