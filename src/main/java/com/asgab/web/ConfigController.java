package com.asgab.web;

import com.asgab.entity.Config;
import com.asgab.service.ConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toConfig(Model model) {
        model.addAttribute("config", configService.get());
        return "config/configForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@ModelAttribute("config") Config config, RedirectAttributes redirectAttributes) {
        configService.update(config);
        redirectAttributes.addFlashAttribute("message", "update success");
        return "redirect:/config";
    }

    @ModelAttribute
    public void getConfig(Model model) {
        model.addAttribute("config", configService.get());
    }
}
