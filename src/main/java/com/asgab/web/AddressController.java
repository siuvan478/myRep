package com.asgab.web;

import com.asgab.core.pagination.Page;
import com.asgab.entity.Address;
import com.asgab.service.AddressService;
import com.asgab.service.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/address")
public class AddressController extends BaseController {

    @Resource
    private AddressService addressService;

    @Resource
    private AreaService areaService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER) int pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                       @RequestParam(value = "sort", defaultValue = "") String sort, ServletRequest request, Model model) {
        //处理搜索条件
        Map<String, Object> params = dealSearchCondition(request, model, "areaId", "address", "contactName");
        params.put("sort", sort);
        Page<Address> page = new Page<>(pageNumber, pageSize, sort, params);
        model.addAttribute("pages", addressService.pageQuery(page));
        model.addAttribute("areas", areaService.getListMapping());
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
        model.addAttribute("areas", areaService.getListMapping());
        return "address/addressForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@ModelAttribute("addressModel") Address address, RedirectAttributes redirectAttributes) {
        addressService.update(address);
        redirectAttributes.addFlashAttribute("message", "update success");
        return "redirect:/address/update/" + address.getId();
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
            model.addAttribute("addressModel", addressService.get(id));
        }
    }
}