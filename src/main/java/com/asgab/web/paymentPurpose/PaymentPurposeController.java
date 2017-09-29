package com.asgab.web.paymentPurpose;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asgab.core.pagination.Page;
import com.asgab.entity.PaymentPurpose;
import com.asgab.service.paymentPurpose.PaymentPurposeService;
import com.asgab.util.Servlets;

/***
 * 支付方式管理 Controller
 * 
 * @author Siuvan
 *
 */
@Controller
@RequestMapping(value = "/paymentPurpose")
public class PaymentPurposeController {

	// 每页显示记录
	private static final String PAGE_SIZE = "10";

	@Resource
	PaymentPurposeService paymentPurposeService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize, @RequestParam(value = "sort", defaultValue = "") String sort,
			ServletRequest request, Model model) {

		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(request.getParameter("pay_code"))) {
			params.put("pay_code", request.getParameter("pay_code"));
		}

		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("search", Servlets.encodeParameterString(params));
		params.put("sort", sort);
		Page<PaymentPurpose> page = new Page<>(pageNumber, pageSize, sort, params);
		model.addAttribute("pages", paymentPurposeService.getAllPaymentPurpose(page));
		return "paymentPurpose/paymentPurposeList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String toCreate(Model model) {
		model.addAttribute("paymentPurpose", new PaymentPurpose());
		model.addAttribute("action", "create");
		return "paymentPurpose/paymentPurposeForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(PaymentPurpose paymentPurpose, RedirectAttributes redirectAttributes) {
		paymentPurpose.setCreateDate(new Date());
		paymentPurpose.setCreateBy(null);// TODO
		paymentPurposeService.addPaymentPurpose(paymentPurpose);
		redirectAttributes.addFlashAttribute("message", "create success");
		return "redirect:/paymentPurpose/update/" + paymentPurpose.getId();
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable("id") Long id, Model model) {
		model.addAttribute("paymentPurpose", paymentPurposeService.getPaymentPurpose(id));
		model.addAttribute("action", "update");
		return "paymentPurpose/paymentPurposeForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("paymentPurpose") PaymentPurpose paymentPurpose, RedirectAttributes redirectAttributes) {
		paymentPurposeService.updatePaymentPurpose(paymentPurpose);
		redirectAttributes.addFlashAttribute("message", "update success");
		return "redirect:/paymentPurpose/update/" + paymentPurpose.getId();
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		paymentPurposeService.deletePaymentPurpose(id);
		redirectAttributes.addFlashAttribute("message", "delete success");
		return "redirect:/paymentPurpose";
	}

	@ModelAttribute
	public void getPaymentPurpose(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("paymentPurpose", paymentPurposeService.getPaymentPurpose(id));
		}
	}

}
