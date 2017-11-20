package com.asgab.web;

import com.asgab.constants.GlobalConstants;
import com.asgab.core.pagination.Page;
import com.asgab.entity.BoxRecord;
import com.asgab.entity.BoxService;
import com.asgab.service.BoxRecordService;
import com.asgab.service.BoxServiceService;
import com.asgab.service.ProductService;
import com.asgab.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/boxService")
public class BoxServiceController extends BaseController {

    @Resource
    private BoxServiceService boxServiceService;

    @Resource
    private BoxRecordService boxRecordService;

    @Resource
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                       @RequestParam(value = "sort", defaultValue = "") String sort, HttpServletRequest request, Model model) {
        //处理搜索条件
        Map<String, Object> params = dealSearchCondition(request, model, "contactName", "productId");
        params.put("sort", sort);
        Page<BoxService> page = new Page<>(pageNumber, pageSize, sort, params);
        model.addAttribute("pages", boxServiceService.pageQuery(page));
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("cycles", CommonUtil.i18nMap(request, GlobalConstants.PRODUCT_CYCLE_ZH, GlobalConstants.PRODUCT_CYCLE_EN));
        model.addAttribute("flags", CommonUtil.i18nMap(request, GlobalConstants.BOX_SERVICE_FLAG_ZH, GlobalConstants.BOX_SERVICE_FLAG_EN));
        model.addAttribute("statuses", CommonUtil.i18nMap(request, GlobalConstants.BOX_SERVICE_STATUS_ZH, GlobalConstants.BOX_SERVICE_STATUS_EN));
        return "boxService/boxServiceList";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        model.addAttribute("boxServiceForm", boxServiceService.get(id));
        model.addAttribute("records", boxRecordService.getBoxRecordList(id));
        model.addAttribute("action", "view");
        model.addAttribute("cycles", CommonUtil.i18nMap(request, GlobalConstants.PRODUCT_CYCLE_ZH, GlobalConstants.PRODUCT_CYCLE_EN));
        model.addAttribute("flags", CommonUtil.i18nMap(request, GlobalConstants.BOX_SERVICE_FLAG_ZH, GlobalConstants.BOX_SERVICE_FLAG_EN));
        model.addAttribute("statuses", CommonUtil.i18nMap(request, GlobalConstants.BOX_SERVICE_STATUS_ZH, GlobalConstants.BOX_SERVICE_STATUS_EN));
        return "boxService/boxServiceView";
    }

    @RequestMapping(value = "record/done", method = RequestMethod.POST)
    public String recordDone(@ModelAttribute("boxRecord") BoxRecord boxRecord, RedirectAttributes redirectAttributes) {
        boxRecordService.boxServiceDone(boxRecord);
        redirectAttributes.addFlashAttribute("message", "update success");
        return "redirect:/boxService/view/" + boxRecord.getServiceId();
    }

    @ModelAttribute("boxService")
    public void getBoxService(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("boxService", boxServiceService.get(id));
        }
    }

    @ModelAttribute("boxRecord")
    public void getBoxRecord(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("boxRecord", boxRecordService.get(id));
        }
    }
}