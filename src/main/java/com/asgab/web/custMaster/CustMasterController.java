package com.asgab.web.custMaster;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.asgab.entity.CustMaster;
import com.asgab.service.custMaster.CustMasterService;
import com.asgab.util.Servlets;

/***
 * user管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /user/ Create page : GET /user/create Create action : POST
 * /user/create Update page : GET /user/update/{id} Update action : POST
 * /user/update Delete action : GET /user/delete/{id}
 * */
@Controller
@RequestMapping(value = "/custMaster")
public class CustMasterController {

	private static final String PAGE_SIZE = "10";
	@Resource
	CustMasterService custMasterService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sort", defaultValue = "") String sort,
			ServletRequest request, Model model) {

		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(request.getParameter("custName"))) {
			params.put("custName", request.getParameter("custName"));
		}
		if (StringUtils.isNotBlank(request.getParameter("custUsername"))) {
			params.put("custUsername", request.getParameter("custUsername"));
		}
		if (StringUtils.isNotBlank(request.getParameter("custPort"))) {
			params.put("custPort", request.getParameter("custPort"));
		}

		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("search", Servlets.encodeParameterString(params));
		params.put("sort", sort);
		Page<CustMaster> page = new Page<>(pageNumber, pageSize, sort, params);
		model.addAttribute("pages", custMasterService.getAllCustMaster(page));
		return "custMaster/custMastersList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String toCreate(Model model) {
		model.addAttribute("custMaster", new CustMaster());
		model.addAttribute("action", "create");
		return "custMaster/custMasterForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(CustMaster custMaster,
			RedirectAttributes redirectAttributes) {
		custMasterService.addCustMaster(custMaster);
		redirectAttributes.addFlashAttribute("message", "create success");
		return "redirect:/custMaster/update/" + custMaster.getId();
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable("id") Long id, Model model) {
		model.addAttribute("custMaster", custMasterService.getCustMaster(id));
		model.addAttribute("action", "update");
		return "custMaster/custMasterForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@ModelAttribute("custMaster") CustMaster custMaster,
			RedirectAttributes redirectAttributes) {
		custMasterService.updateCustMaster(custMaster);
		redirectAttributes.addFlashAttribute("message", "update success");
		return "redirect:/custMaster/update/" + custMaster.getId();
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		custMasterService.deleteCustMaster(id);
		redirectAttributes.addFlashAttribute("message", "delete success");
		return "redirect:/custMaster";
	}

	@RequestMapping(value = "checkCustUsername", method = RequestMethod.GET)
	public String checkCustUsername(HttpServletRequest request,
			HttpServletResponse response) {
		String result = "false";
		String custUsername = request.getParameter("custUsername");
		if (custMasterService.existByCustUsername(custUsername))
			result = "true";
		System.out.println(result);
		return result;
	}

	@ModelAttribute
	public void getCustMaster(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("custMaster",
					custMasterService.getCustMaster(id));
		}
	}

}
