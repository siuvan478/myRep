package com.asgab.web;

import com.asgab.core.pagination.Page;
import com.asgab.entity.Address;
import com.asgab.entity.Address;
import com.asgab.entity.City;
import com.asgab.service.AddressService;
import com.asgab.service.AddressService;
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
@RequestMapping(value = "/address")
public class AddressController extends BaseController {

    @Resource
    private AddressService addressService;

    @Resource
    private CityService cityService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                       @RequestParam(value = "sort", defaultValue = "") String sort, ServletRequest request, Model model) {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(request.getParameter("cityId"))) {
            params.put("cityId", request.getParameter("cityId"));
        }
        if (StringUtils.isNotBlank(request.getParameter("addressId"))) {
            params.put("addressId", request.getParameter("addressId"));
        }
        if (StringUtils.isNotBlank(request.getParameter("address"))) {
            params.put("address", request.getParameter("address"));
        }
        if (StringUtils.isNotBlank(request.getParameter("status"))) {
            params.put("status", request.getParameter("status"));
        }
        // 将搜索条件编码成字符串，用于排序，分页的URL
        model.addAttribute("search", Servlets.encodeParameterString(params));
        params.put("sort", sort);
        Page<Address> page = new Page<>(pageNumber, pageSize, sort, params);
        model.addAttribute("pages", addressService.pageQuery(page));
        model.addAttribute("cities", cityService.getCitiesMapping());
        return "address/addressList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String toCreate(Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("action", "create");
        return "address/addressForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(Address address, RedirectAttributes redirectAttributes) {
        addressService.save(address);
        redirectAttributes.addFlashAttribute("message", "save success");
        return "redirect:/address";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("address", addressService.get(id));
        model.addAttribute("action", "update");
        return "address/addressForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@ModelAttribute("address") Address address, RedirectAttributes redirectAttributes) {
        addressService.update(address);
        redirectAttributes.addFlashAttribute("message", "update success");
        return "redirect:/address";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        addressService.delete(id);
        redirectAttributes.addFlashAttribute("message", "delete success");
        return "redirect:/address";
    }

    @ModelAttribute
    public void getAddress(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("address", addressService.get(id));
        }
    }
}