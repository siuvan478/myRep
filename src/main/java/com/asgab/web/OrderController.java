package com.asgab.web;

import com.asgab.constants.GlobalConstants;
import com.asgab.core.pagination.Page;
import com.asgab.entity.Order;
import com.asgab.service.OrderService;
import com.asgab.service.ProductService;
import com.asgab.service.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;

    @Resource
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                       @RequestParam(value = "sort", defaultValue = "") String sort, ServletRequest request, Model model) {
        //处理搜索条件
        Map<String, Object> params = dealSearchCondition(request, model, "orderNo", "productId", "status");
        params.put("sort", sort);
        Page<Order> page = new Page<>(pageNumber, pageSize, sort, params);
        model.addAttribute("pages", orderService.pageQuery(page));
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("statuses", GlobalConstants.ORDER_STATUS_ZH);
        model.addAttribute("cycles", GlobalConstants.PRODUCT_CYCLE_ZH);
        return "order/orderList";
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("orderForm", orderService.get(id));
        model.addAttribute("action", "view");
        model.addAttribute("statuses", GlobalConstants.ORDER_STATUS_ZH);
        model.addAttribute("cycles", GlobalConstants.PRODUCT_CYCLE_ZH);
        return "order/orderView";
    }

    @RequestMapping(value = "audit/{id}", method = RequestMethod.GET)
    public String toAudit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("orderForm", orderService.get(id));
        return "include/auditOrderForm";
    }

    @RequestMapping(value = "audit", method = RequestMethod.POST)
    public String audit(Order order, RedirectAttributes redirectAttributes) {
        try {
            orderService.audit(order);
            redirectAttributes.addFlashAttribute("message", "audit success");
        } catch (ServiceException se) {
            redirectAttributes.addFlashAttribute("error_message", se.getMessage());
        }
        return "redirect:/order";
    }

    @ModelAttribute
    public void getOrder(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("order", orderService.get(id));
        }
    }
}