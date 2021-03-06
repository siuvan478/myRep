package com.asgab.web;

import com.asgab.core.pagination.Page;
import com.asgab.entity.Product;
import com.asgab.entity.Scale;
import com.asgab.service.ProductService;
import com.asgab.service.ScaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ScaleService scaleService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER) int pageNumber,
                       @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                       @RequestParam(value = "sort", defaultValue = "") String sort, ServletRequest request, Model model) {
        //处理搜索条件
        Map<String, Object> params = dealSearchCondition(request, model, "productName");
        params.put("sort", sort);
        Page<Product> page = new Page<>(pageNumber, pageSize, sort, params);
        model.addAttribute("pages", productService.getAll(page));
        return "product/productList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String toCreate(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("action", "create");
        return "product/productForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(Product product, RedirectAttributes redirectAttributes) {
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "save success");
        return "redirect:/product";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.get(id));
        model.addAttribute("action", "update");
        model.addAttribute("scales", productService.getProductScales(id));
        return "product/productForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes) {
        productService.update(product);
        redirectAttributes.addFlashAttribute("message", "update success");
        return "redirect:/product";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("message", "delete success");
        return "redirect:/product";
    }

    @ModelAttribute("product")
    public void getProduct(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("product", productService.get(id));
        }
    }

    @RequestMapping(value = "showUploadModal/{imageType}", method = RequestMethod.GET)
    public String showUploadModal(@PathVariable("imageType") String imageType, Model model) {
        model.addAttribute("imageType", imageType);
        model.addAttribute("upload_url", "/file/upload");
        return "include/upload";
    }

    /*===================================================Scale 部分===================================================*/
    @RequestMapping(value = "scale/create/{pid}", method = RequestMethod.GET)
    public String toScaleCreate(@PathVariable(value = "pid") Long productId, Model model) {
        Scale scaleForm = new Scale();
        scaleForm.setProductId(productId);
        scaleForm.setNeedQuote(0);
        scaleForm.setOneMonthPrice(new BigDecimal(0));
        scaleForm.setThreeMonthPrice(new BigDecimal(0));
        scaleForm.setSixMonthPrice(new BigDecimal(0));
        scaleForm.setTwelveMonthPrice(new BigDecimal(0));
        scaleForm.setNum(100);
        model.addAttribute("scaleForm", scaleForm);
        model.addAttribute("action2", "create");
        return "include/scaleForm";
    }

    @RequestMapping(value = "scale/create", method = RequestMethod.POST)
    public String createScale(Scale scale, RedirectAttributes redirectAttributes) {
        scaleService.save(scale);
        redirectAttributes.addFlashAttribute("message", "save scale success");
        return "redirect:/product/update/" + scale.getProductId();
    }

    @RequestMapping(value = "scale/update/{id}", method = RequestMethod.GET)
    public String toScaleUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("scaleForm", scaleService.get(id));
        model.addAttribute("action2", "update");
        return "include/scaleForm";
    }

    @RequestMapping(value = "scale/update", method = RequestMethod.POST)
    public String scaleUpdate(@ModelAttribute("scale") Scale scale, RedirectAttributes redirectAttributes) {
        scaleService.update(scale);
        redirectAttributes.addFlashAttribute("message", "update scale success");
        return "redirect:/product/update/" + scale.getProductId();
    }

    @RequestMapping(value = "scale/delete/{pId}/{id}", method = RequestMethod.GET)
    public String deleteScale(@PathVariable("pId") Long pId, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        scaleService.delete(id);
        redirectAttributes.addFlashAttribute("message", "delete scale success");
        return "redirect:/product/update/" + pId;
    }

    @ModelAttribute("scale")
    public void getScale(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("scale", scaleService.get(id));
        }
    }
}