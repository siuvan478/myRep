package com.asgab.service.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.asgab.entity.CustMaster;
import com.asgab.entity.Mail;
import com.asgab.entity.PayTranDetail;
import com.asgab.entity.PayTranHeader;
import com.asgab.entity.Process;
import com.asgab.entity.User;
import com.asgab.repository.CustMasterMapper;
import com.asgab.repository.ProcessMapper;
import com.asgab.repository.UserMapper;
import com.asgab.service.MailService;
import com.asgab.service.paytran.PayTranHeaderService;
import com.asgab.util.CommonUtil;
import com.asgab.util.Cryptogram;
import com.asgab.web.AutoEmailNotification;

@Component
@Transactional
public class ProcessService {

	// 被拒绝
	public static String TEST_MAIL_REJECT = "";
	public static String TEST_MAIL_FINANCE_CHECK = "";
	public static String TEST_MAIL_FINANCE_CONFIRM = "";
	public static String TEST_MAIL_OPS_CONFIRM = "";
	// NOTICE 流程完成通知
	public static String TEST_MAIL_DONE_NOTICE = "";
	public static String SERVER_DOMAIN = "";


	static{
		ResourceBundle bundle = ResourceBundle.getBundle( "mail");
		TEST_MAIL_REJECT = bundle.getString("mail.default.reject");
		TEST_MAIL_FINANCE_CHECK = bundle.getString("mail.default.check");
		TEST_MAIL_FINANCE_CONFIRM = bundle.getString("mail.default.finance.confirm");
		TEST_MAIL_OPS_CONFIRM = bundle.getString("mail.default.ops.confirm");
		TEST_MAIL_DONE_NOTICE = bundle.getString("mail.default.done");
		SERVER_DOMAIN = bundle.getString("server.domain");
	}

	@Resource
	private ProcessMapper processMapper;

	@Autowired
	private PayTranHeaderService payTranHeaderService;

	@Resource
	private CustMasterMapper custMasterMapper;

	@Autowired
	private MailService mailService;

	@Resource
	private UserMapper userMapper;

	public List<Process> get(Map<String, Object> parms) {
		return processMapper.get(parms);
	}

	public Process getById(Long processId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("processId", processId);
		List<Process> processes = get(map);
		return (processes != null && processes.size() == 1) ? processes.get(0)
				: null;
	}

	public void save(Process process) {
		processMapper.save(process);
	}

	public void update(Process process) {
		processMapper.update(process);
	}

	// 第一封邮件
	@Transactional(rollbackFor = Exception.class)
	public void noticeFinance(Long paytranNum) throws Exception{
		List<String> receivers = getReceiver(paytranNum,"finance");
		if(receivers.size()==0){
			receivers.add(TEST_MAIL_FINANCE_CHECK);
		}
		String randomKey = UUID.randomUUID().toString();
		for(String receiver:receivers){
			// 创建一个新的流程
			Process process = new Process();
			process.setCreateDate(new Date());
			process.setIsUpdated('0');
			process.setPayTranNum(paytranNum);
			process.setRandomKey(randomKey);
			process.setRandomIdentification(UUID.randomUUID().toString());
			process.setUpdateBy(receiver);
			process.setStatus(CommonUtil.STATUS_NEW);
			processMapper.save(process);
			//
			Mail mail = new Mail();
			mail.setCreateDate(new Date());
			mail.setReceiver(receiver);
			mail.setSubject("财务check");
			mail.setPaytranNum(paytranNum);
			mail.setTemplate(AutoEmailNotification.EMAIL_TEMPLATE_NAME_1);
			mail.setProcessId(process.getProcessId());
			mail.setUrlConfirm("http://"+SERVER_DOMAIN+"/csa/process/toFinanceOpin/confirm/"+Cryptogram.encodeId(process.getProcessId()+"")+"/"+process.getRandomKey()+"/"+process.getRandomIdentification());
			mail.setUrlReject("http://"+SERVER_DOMAIN+"/csa/process/toFinanceOpin/reject/"+Cryptogram.encodeId(process.getProcessId()+"")+"/"+process.getRandomKey()+"/"+process.getRandomIdentification());
			mailService.save(mail);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void reject(Process processDB,Long paytranNum,String description) throws Exception{
		if(processDB != null){
			// 首先修改老的数据
			processDB.setStatus(CommonUtil.STATUS_REJECT);
			processDB.setUpdateDate(new Date());
			processDB.setIsUpdated('1');
			processDB.setRemarks("财务邮件拒绝");
			processDB.setDescription(description);
			processMapper.update(processDB);
		}
		//  发邮件给AM,客户重新上传资料
		List<String> receivers = getReceiver(paytranNum, "AM");
		if(receivers.size()==0){
			receivers.add(TEST_MAIL_REJECT);
		}
		for(String receiver:receivers){
			Mail mail = new Mail();
			mail.setReceiver(receiver);
			mail.setSubject("Payment transaction rejected");
			mail.setTemplate(AutoEmailNotification.EMAIL_TEMPLATE_NAME_REJECT);
			mail.setCreateDate(new Date());
			mail.setPaytranNum(paytranNum);
			mail.setDescription(description);
			mailService.save(mail);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void check(Process processDB,Long paytranNum) throws Exception{
		// 首先修改老的数据
		if(processDB!=null){
			processDB.setStatus(CommonUtil.STATUS_CHECK);
			processDB.setUpdateDate(new Date());
			processDB.setIsUpdated('1');
			processDB.setRemarks("财务check通过");
			processMapper.update(processDB);
		}
		// 新建新的流程
		List<String> receivers = getReceiver(paytranNum,"finance");
		if(receivers.size()==0){
			receivers.add(TEST_MAIL_FINANCE_CONFIRM);
		}
		String newRandomKey = UUID.randomUUID().toString();
		for(String receiver:receivers){
			Process nextProcess = new Process();
			nextProcess.setPayTranNum(paytranNum);
			nextProcess.setRandomKey(newRandomKey);
			nextProcess.setRandomIdentification(UUID.randomUUID().toString());
			nextProcess.setStatus(CommonUtil.STATUS_CHECK);
			nextProcess.setCreateDate(new Date());
			nextProcess.setUpdateBy(receiver);
			nextProcess.setIsUpdated('0');
			processMapper.save(nextProcess);
			//发邮件给finance confirm. 这里发邮件用nextProcess.processId
			Mail mail = new Mail();
			mail.setCreateDate(new Date());
			mail.setReceiver(receiver);
			mail.setSubject("财务check通过,下一步confirm");
			mail.setTemplate(AutoEmailNotification.EMAIL_TEMPLATE_NAME_2);
			mail.setPaytranNum(paytranNum);
			mail.setProcessId(nextProcess.getProcessId());
			mail.setUrlConfirm("http://"+SERVER_DOMAIN+"/csa/process/financeConfirm/"+Cryptogram.encodeId(nextProcess.getProcessId()+"")+"/"+nextProcess.getRandomKey()+"/"+nextProcess.getRandomIdentification());
			mailService.save(mail);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void financeConfirm(Process processDB,Long paytranNum) throws Exception{
		if(processDB!=null){
			//修改老数据
			processDB.setStatus(CommonUtil.STATUS_FINANCE_CONFIRM);
			processDB.setUpdateDate(new Date());
			processDB.setIsUpdated('1');
			processDB.setRemarks("财务confirm通过");
			processMapper.update(processDB);
		}
		// 新建 发送给ops
		List<String> receivers = getReceiver(paytranNum,"ops");
		if(receivers.size()==0){
			receivers.add(TEST_MAIL_OPS_CONFIRM);
		}
		String newRandomKey = UUID.randomUUID().toString();
		for(String receiver:receivers){
			Process nextProcess = new Process();
			nextProcess.setPayTranNum(paytranNum);
			nextProcess.setRandomKey(newRandomKey);
			nextProcess.setRandomIdentification(UUID.randomUUID().toString());
			nextProcess.setStatus(CommonUtil.STATUS_OPS_CONFIRM);
			nextProcess.setCreateDate(new Date());
			nextProcess.setUpdateBy(receiver);
			nextProcess.setIsUpdated('0');
			processMapper.save(nextProcess);
			// 发邮件给AM confirm. 这里发邮件用nextProcess.processId
			Mail mail = new Mail();
			mail.setCreateDate(new Date());
			mail.setReceiver(receiver);
			mail.setSubject("财务confirm. 发邮件给ops");
			mail.setTemplate(AutoEmailNotification.EMAIL_TEMPLATE_NAME_3);
			mail.setPaytranNum(paytranNum);
			mail.setProcessId(nextProcess.getProcessId());
			mail.setUrlConfirm("http://"+SERVER_DOMAIN+"/csa/process/opsConfirm/"+Cryptogram.encodeId(nextProcess.getProcessId()+"")+"/"+nextProcess.getRandomKey()+"/"+nextProcess.getRandomIdentification());
			mailService.save(mail);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void opsConfirm(Process processDB,Long paytranNum) throws Exception{
		if(processDB != null){
			//修改老数据
			processDB.setStatus(CommonUtil.STATUS_OPS_CONFIRM);
			processDB.setUpdateDate(new Date());
			processDB.setIsUpdated('1');
			processDB.setRemarks("Ops confirm通过");
			processMapper.update(processDB);
		}
		// 新建一条记录完成状态 最后一封发给AM和页面录入的邮箱
		List<String> receivers = getReceiver(paytranNum,"AM");
		if(receivers.size()==0){
			receivers.add(TEST_MAIL_DONE_NOTICE);
		}
		for(String receiver:receivers){
			Process lastestProcess = new Process();
			lastestProcess.setPayTranNum(paytranNum);
			lastestProcess.setStatus(CommonUtil.STATUS_DONE);
			lastestProcess.setCreateDate(new Date());
			lastestProcess.setDescription("流程完成");
			processMapper.save(lastestProcess);
			//通知所有人
			Mail mail = new Mail();
			mail.setCreateDate(new Date());
			mail.setReceiver(receiver);
			mail.setSubject("流程完成");
			mail.setTemplate(AutoEmailNotification.EMAIL_TEMPLATE_NAME_4);
			mail.setPaytranNum(paytranNum);
			mail.setProcessId(lastestProcess.getProcessId());
			mailService.save(mail);
		}
		// 页面录入的邮箱
		PayTranHeader payTranHeader =  payTranHeaderService.get(paytranNum);
		Mail mail = new Mail();
		mail.setCreateDate(new Date());
		mail.setReceiver(payTranHeader.getEmail());
		mail.setSubject("流程完成");
		mail.setTemplate(AutoEmailNotification.EMAIL_TEMPLATE_NAME_5);
		mail.setPaytranNum(paytranNum);
		mailService.save(mail);
	}

	private List<String> getReceiver(Long paytranNum,String roles){
		PayTranHeader payTranHeader = payTranHeaderService.get(paytranNum);
		Map<String,String> mailMap = new HashMap<String,String>();
		boolean custMasterExist = true;
		// 如果是财务就从user里面找
		if("finance".equals(roles)){
			for(PayTranDetail payTranDetail:payTranHeader.getPayTranDetails()){
				String bdUserName = payTranDetail.getBdUserName();
				CustMaster custMaster = custMasterMapper.findByCustUsername(bdUserName);
				if(custMaster!=null){
					String fin_email = custMaster.getFin_email();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("roles", roles);
					map.put("financeEmail", fin_email);
					List<User> users = userMapper.search(map);
					if(users!=null && users.size()>0){
						for(User user:users){
							if(StringUtils.isNotBlank(user.getEmail())){
								// 在user里面找邮箱地址
								mailMap.put(user.getEmail(), user.getEmail());
							}
							// 这里理论不不可能为空
						}
					}
				}else{
					// 找不到百度用户
					custMasterExist = false;
				}
			}
		}
		// 否则就custmas找
		else{
			for(PayTranDetail payTranDetail:payTranHeader.getPayTranDetails()){
				CustMaster custMaster = custMasterMapper.findByCustUsername(payTranDetail.getBdUserName());
				if(custMaster!=null){
					if("ops".equalsIgnoreCase(roles)){
						if(StringUtils.isNotBlank(custMaster.getOps_email())){
							mailMap.put(custMaster.getOps_email(),custMaster.getOps_email());
						}else{
							//只有百度用户资料,但是邮箱为空,还是user里面找
							custMasterExist = false;
						}
					}else if("AM".equalsIgnoreCase(roles)){
						if(StringUtils.isNotBlank(custMaster.getAm_email())){
							mailMap.put(custMaster.getAm_email(), custMaster.getAm_email());
						}else{
							custMasterExist = false;
						}
					}
				}
				// 否则在user里面找
				else{
					custMasterExist = false;
				}

			}
		}

		// 不存在的. 统一在user里面找
		if(!custMasterExist){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("roles", roles);
			map.put("emailaudit", "Y");
			List<User> users = userMapper.search(map);
			if(users!=null && users.size()>0){
				for(User user:users){
					if(StringUtils.isNotBlank(user.getEmail())){
						mailMap.put(user.getEmail(), user.getEmail());
					}
				}
			}
		}


		List<String> mails = new ArrayList<String>();
		Iterator<Entry<String, String>> entrys= mailMap.entrySet().iterator();
		while(entrys.hasNext()){
			mails.add(entrys.next().getKey());
		}
		return mails;
	}
}
