package com.asgab.web;

import com.asgab.core.pagination.Page;
import com.asgab.entity.Area;
import com.asgab.entity.City;
import com.asgab.service.AreaService;
import com.asgab.service.CityService;
import com.asgab.util.Servlets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping(value = "/area")
public class AreaController extends BaseController {

    @Resource
    private AreaService areaService;

    @Resource
    private CityService cityService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                       @RequestParam(value = "sort", defaultValue = "") String sort, ServletRequest request, Model model) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(request.getParameter("name"))) {
            params.put("name", request.getParameter("name"));
        }
        if (StringUtils.isNotBlank(request.getParameter("nameEN"))) {
            params.put("nameEN", request.getParameter("nameEN"));
        }
        if (StringUtils.isNotBlank(request.getParameter("cityId"))) {
            params.put("cityId", request.getParameter("cityId"));
        }
        if (StringUtils.isNotBlank(request.getParameter("status"))) {
            params.put("status", request.getParameter("status"));
        }
        // 将搜索条件编码成字符串，用于排序，分页的URL
        model.addAttribute("search", Servlets.encodeParameterString(params));
        params.put("sort", sort);
        Page<Area> page = new Page<>(pageNumber, pageSize, sort, params);
        model.addAttribute("pages", areaService.pageQuery(page));
        model.addAttribute("cities", getCityMapping());
        //<tags:selectbox name="status" map="${statuses}" value="${pages.searchMap['status']}"></tags:selectbox>
        return "area/areaList";
    }

    private Map<String, String> getCityMapping() {
        List<City> citys = cityService.findAll();
        final Map<String, String> cityMappings = new TreeMap<>();
        for (City c : citys) {
            cityMappings.put(c.getId().toString(), c.getName());
        }
        return cityMappings;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String toCreate(Model model) {
        model.addAttribute("area", new Area());
        model.addAttribute("action", "create");
        model.addAttribute("cities", getCityMapping());
        return "area/areaForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(Area area, RedirectAttributes redirectAttributes) {
        areaService.save(area);
        redirectAttributes.addFlashAttribute("message", "save success");
        return "redirect:/area";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("area", areaService.get(id));
        model.addAttribute("action", "update");
        model.addAttribute("cities", getCityMapping());
        return "area/areaForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@ModelAttribute("area") Area area, RedirectAttributes redirectAttributes) {
        areaService.update(area);
        redirectAttributes.addFlashAttribute("message", "update success");
        return "redirect:/area";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        areaService.delete(id);
        redirectAttributes.addFlashAttribute("message", "delete success");
        return "redirect:/area";
    }

    @ModelAttribute
    public void getArea(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("area", areaService.get(id));
        }
    }
}