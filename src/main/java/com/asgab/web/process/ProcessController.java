package com.asgab.web.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asgab.entity.PayTranHeader;
import com.asgab.entity.Process;
import com.asgab.repository.PayTranHeaderMapper;
import com.asgab.service.MailService;
import com.asgab.service.process.ProcessService;
import com.asgab.util.CommonUtil;
import com.asgab.util.Cryptogram;

@Controller
@RequestMapping(value = "/process")
public class ProcessController {
	// 1 找不到客户资料发邮件给AM
	// 2 找到客户资料发邮件给财务
	// 3 财务不同意发邮件给AM和客户重新修改资料
	// 4 财务同意发邮件给AM
	// 5 AM同意 发邮件给客户,入款完成
	
	@Autowired
	private ProcessService processService;
	
	@Autowired
	private MailService mailService;
	
	@Resource
	private PayTranHeaderMapper payTranHeaderMapper;
	
	// 去财务拒绝理由页面,
	@RequestMapping(value="toFinanceOpin/{opin}/{encodedId}/{randomKey}/{randomIdentification}", method=RequestMethod.GET)
	public String toFinanceOpin(@PathVariable("opin") String opin,@PathVariable("encodedId") String encodedId,@PathVariable("randomKey") String randomKey,@PathVariable("randomIdentification") String randomIdentification,Model model,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Process process = checkAccessable(encodedId,randomKey,randomIdentification);
		if(process!=null&&checkPaytranStatus(process,CommonUtil.STATUS_NEW)){
			model.addAttribute("process",process);
			if("confirm".equalsIgnoreCase(opin)){
				String message =  check(encodedId,randomKey,randomIdentification,request);
				//check 通过,修改状态
				PayTranHeader payTranHeader = payTranHeaderMapper.get(process.getPayTranNum());
				payTranHeader.setStatus(CommonUtil.STATUS_CHECK);
				payTranHeaderMapper.update(payTranHeader);
				redirectAttributes.addFlashAttribute("message", message);
				return "redirect:/process/message";
			}else{
				model.addAttribute("action", "reject");
			}
		}else{
			String message =  getConfirmedMessage(request, false);
			redirectAttributes.addFlashAttribute("message", message);
			redirectAttributes.addFlashAttribute("error", "error");
			return "redirect:/process/message";
		}
		return "process/toFinanceCheck"; 
	}
	

	// 3 财务检查不通过
	@RequestMapping(value="reject" , method=RequestMethod.POST)
	public String reject(Process process,RedirectAttributes redirectAttributes,HttpServletRequest request){
		Process processDB = checkAccessable(process.getEncodedId()+"",process.getRandomKey(),process.getRandomIdentification());
		if(processDB!=null&&checkPaytranStatus(processDB,CommonUtil.STATUS_NEW)){
			try{
				//reject,修改状态
				PayTranHeader payTranHeader = payTranHeaderMapper.get(processDB.getPayTranNum());
				payTranHeader.setStatus(CommonUtil.STATUS_REJECT);
				payTranHeaderMapper.update(payTranHeader);
				processService.reject(processDB, payTranHeader.getTranNum() ,process.getDescription());
			}catch(Exception e){
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message", getSysError(request));
				redirectAttributes.addFlashAttribute("error", "error");
				return "redirect:/process/message"; 
			}
			redirectAttributes.addFlashAttribute("message", CommonUtil.i18nStr(request, "交易被拒绝(交易编号:"+processDB.getPayTranNum()+")", "Transaction Rejected (Transaction Number:"+processDB.getPayTranNum()+")"));
		}else{
			redirectAttributes.addFlashAttribute("message", getConfirmedMessage(request, false));
			redirectAttributes.addFlashAttribute("error", "error");
		}
		return "redirect:/process/message";
	}
	
	// 4 财务检查通过
	public String check(String encodedId,String randomKey,String randomIdentification,HttpServletRequest request){
		String message = "";
		Process processDB = checkAccessable(encodedId,randomKey,randomIdentification);
		if(processDB!=null){
			try{
				//修改状态
				PayTranHeader payTranHeader = payTranHeaderMapper.get(processDB.getPayTranNum());
				payTranHeader.setStatus(CommonUtil.STATUS_CHECK);
				payTranHeaderMapper.update(payTranHeader);
				processService.check(processDB,payTranHeader.getTranNum());
			}catch(Exception e){
				e.printStackTrace();
				return getSysError(request);
			}
			message = getConfirmedMessage(request, true);
		}else{
			message = getConfirmedMessage(request, false);
		}
		return message;
	}
	
	// 5 finance同意
	@RequestMapping(value="financeConfirm/{encodedId}/{randomKey}/{randomIdentification}" , method=RequestMethod.GET)
	public String financeConfirm(@PathVariable("encodedId") String encodedId,@PathVariable("randomKey") String randomKey,@PathVariable("randomIdentification") String randomIdentification,RedirectAttributes redirectAttributes ,HttpServletRequest request){
		Process processDB = checkAccessable(encodedId,randomKey,randomIdentification);
		if(processDB!=null&&checkPaytranStatus(processDB,CommonUtil.STATUS_CHECK)){
			try{
				//修改状态
				PayTranHeader payTranHeader = payTranHeaderMapper.get(processDB.getPayTranNum());
				payTranHeader.setStatus(CommonUtil.STATUS_FINANCE_CONFIRM);
				payTranHeaderMapper.update(payTranHeader);
				processService.financeConfirm(processDB,payTranHeader.getTranNum());
			}catch(Exception e){
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message", getSysError(request));
				redirectAttributes.addFlashAttribute("error", "error");
				return "redirect:/process/message"; 
			}
			redirectAttributes.addFlashAttribute("message", getConfirmedMessage(request, true));
		}else{
			redirectAttributes.addFlashAttribute("message", getConfirmedMessage(request, false));
			redirectAttributes.addFlashAttribute("error", "error");
		}
		return "redirect:/process/message"; 
	}
	
	// AM确认
	@RequestMapping(value="opsConfirm/{encodedId}/{randomKey}/{randomIdentification}" , method=RequestMethod.GET)
	public String opsConfirm(@PathVariable("encodedId") String encodedId,@PathVariable("randomKey") String randomKey,@PathVariable("randomIdentification") String randomIdentification ,RedirectAttributes redirectAttributes,HttpServletRequest request){
		Process processDB = checkAccessable(encodedId,randomKey,randomIdentification);
		if(processDB!=null&&checkPaytranStatus(processDB,CommonUtil.STATUS_FINANCE_CONFIRM)){
			try{
				//修改状态
				PayTranHeader payTranHeader = payTranHeaderMapper.get(processDB.getPayTranNum());
				// opsConfirm 和 done
				payTranHeader.setStatus(CommonUtil.STATUS_DONE);
				payTranHeaderMapper.update(payTranHeader);
				processService.opsConfirm(processDB,payTranHeader.getTranNum());
			}catch(Exception e){
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message", getSysError(request));
				redirectAttributes.addFlashAttribute("error", "error");
				return "redirect:/process/message"; 
			}
			redirectAttributes.addFlashAttribute("message", getConfirmedMessage(request, true));
		} else {
			redirectAttributes.addFlashAttribute("message", getConfirmedMessage(request, false));
			redirectAttributes.addFlashAttribute("error", "error");
		}

		return "redirect:/process/message"; 
	}
	
	@RequestMapping(value="message",method=RequestMethod.GET)
	public String message(){
		return "process/message";
	}
	
	/**
	 * 验证是否合法
	 * @param encodedId 这里是加密的id
	 * @param randomKey
	 * @return
	 */
	private Process checkAccessable(String encodedId,String randomKey,String randomIdentification){
		Process process = null;
		Long processId=Long.parseLong(Cryptogram.decodeId(encodedId));
		Map<String , Object> map = new HashMap<String,Object>();
		map.put("processId", processId);
		map.put("randomKey", randomKey);
		map.put("randomIdentification", randomIdentification);
		List<Process> processes = processService.get(map);
		if(processes!=null && processes.size()==1){
			process = processes.get(0);
		}
		return process;
	}
	
	private String getConfirmedMessage(HttpServletRequest request,boolean isSuccess){
		String message = "";
		if(isSuccess){
			message = "zh".equalsIgnoreCase(request.getLocale().getLanguage())?"感谢您的审批":"thanks for your approving";
		}else{
			message = "zh".equalsIgnoreCase(request.getLocale().getLanguage())?"链接已过期或不是有效链接":"The link has expired or illegal";
		}
		return message;
	}
	
	private String getSysError(HttpServletRequest request){
		return "zh".equalsIgnoreCase(request.getLocale().getLanguage())?"系统出错":"System Error";
	}
	
	/**
	 * 检查当前的状态是否符合审批状态条件
	 * @param process
	 * @param statusNew
	 * @return
	 */
	private boolean checkPaytranStatus(Process process, Character status) {
		boolean flag = false;
		PayTranHeader payTranHeader =  payTranHeaderMapper.get(process.getPayTranNum());
		if(payTranHeader!=null&&payTranHeader.getStatus()==status){
			flag = true;
		}
		return flag;
	}

}
