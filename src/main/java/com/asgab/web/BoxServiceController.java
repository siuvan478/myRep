package com.asgab.web;

import com.asgab.core.pagination.Page;
import com.asgab.entity.BoxService;
import com.asgab.service.BoxServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/boxService")
public class BoxServiceController extends BaseController {

    @Resource
    private BoxServiceService boxServiceService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                       @RequestParam(value = "sort", defaultValue = "") String sort, ServletRequest request, Model model) {
        //处理搜索条件
        Map<String, Object> params = dealSearchCondition(request, model);
        params.put("sort", sort);
        Page<BoxService> page = new Page<>(pageNumber, pageSize, sort, params);
        model.addAttribute("pages", boxServiceService.pageQuery(page));
        return "boxService/boxServiceList";
    }

    @ModelAttribute
    public void getBoxService(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("boxService", boxServiceService.get(id));
        }
    }
}