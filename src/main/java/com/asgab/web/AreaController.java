package com.asgab.web;

import com.asgab.core.pagination.Page;
import com.asgab.entity.Area;
import com.asgab.entity.City;
import com.asgab.service.AreaService;
import com.asgab.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
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
        //处理搜索条件
        Map<String, Object> params = dealSearchCondition(request, model, "name", "nameEN", "cityId", "status");
        params.put("sort", sort);
        Page<Area> page = new Page<>(pageNumber, pageSize, sort, params);
        model.addAttribute("pages", areaService.pageQuery(page));
        model.addAttribute("cities", getCityMapping());
        return "area/areaList";
    }

    private Map<String, String> getCityMapping() {
        List<City> cities = cityService.findAll();
        final Map<String, String> cityMappings = new TreeMap<>();
        for (City c : cities) {
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