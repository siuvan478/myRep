package com.asgab.web;

import com.asgab.constants.GlobalConstants;
import com.asgab.core.pagination.Page;
import com.asgab.entity.BoxService;
import com.asgab.service.BoxRecordService;
import com.asgab.service.BoxServiceService;
import com.asgab.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
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
                       @RequestParam(value = "sort", defaultValue = "") String sort, ServletRequest request, Model model) {
        //处理搜索条件
        Map<String, Object> params = dealSearchCondition(request, model, "contactName", "productId");
        params.put("sort", sort);
        Page<BoxService> page = new Page<>(pageNumber, pageSize, sort, params);
        model.addAttribute("pages", boxServiceService.pageQuery(page));
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("cycles", GlobalConstants.PRODUCT_CYCLE_ZH);
        model.addAttribute("flags", GlobalConstants.BOX_SERVICE_FLAG_ZH);
        model.addAttribute("statuses", GlobalConstants.BOX_SERVICE_STATUS_ZH);
        return "boxService/boxServiceList";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("boxServiceForm", boxServiceService.get(id));
        model.addAttribute("records", boxRecordService.getBoxRecordList(id));
        model.addAttribute("action", "view");
        model.addAttribute("cycles", GlobalConstants.PRODUCT_CYCLE_ZH);
        model.addAttribute("flags", GlobalConstants.BOX_SERVICE_FLAG_ZH);
        model.addAttribute("statuses", GlobalConstants.BOX_SERVICE_STATUS_ZH);
        return "boxService/boxServiceView";
    }

    @RequestMapping(value = "record/done/{id}", method = RequestMethod.GET)
    public String toScaleUpdate(@PathVariable("id") Long recordId, Model model) {
        model.addAttribute("recordForm", boxRecordService.get(recordId));
        return "include/boxRecordForm";
    }

    @ModelAttribute
    public void getBoxService(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("boxService", boxServiceService.get(id));
        }
    }
}