package com.asgab.service.paytran;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.asgab.entity.Process;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.asgab.core.pagination.Page;
import com.asgab.entity.CurrMaster;
import com.asgab.entity.CustMaster;
import com.asgab.entity.CustPaymentInfo;
import com.asgab.entity.PayTranAttachement;
import com.asgab.entity.PayTranDetail;
import com.asgab.entity.PayTranHeader;
import com.asgab.repository.CustMasterMapper;
import com.asgab.repository.PayTranAttachmentMapper;
import com.asgab.repository.PayTranDetailMapper;
import com.asgab.repository.PayTranHeaderMapper;
import com.asgab.service.MailService;
import com.asgab.service.account.ShiroDbRealm.ShiroUser;
import com.asgab.service.currMaster.CurrMasterService;
import com.asgab.service.process.ProcessService;
import com.asgab.util.CommonUtil;

@Component
@Transactional
public class PayTranHeaderService {

	@Resource
	private PayTranHeaderMapper payTranHeaderMapper;

	@Resource
	private PayTranDetailMapper payTranDetailMapper;

	@Resource
	private PayTranAttachmentMapper payTranAttachmentMapper;

	@Resource
	private CustMasterMapper custMasterMapper;

	@Resource
	private ProcessService processService;

	@Autowired
	private MailService mailService;
	
	@Autowired
	private CurrMasterService currMasterService;

	public PayTranHeader get(Long tranNum) {
		PayTranHeader payTranHeader = payTranHeaderMapper.get(tranNum);
		if(payTranHeader!=null){
			payTranHeader.setPayTranDetails(payTranDetailMapper.get(tranNum));
			payTranHeader.setPayTranAttachements(payTranAttachmentMapper.get(tranNum));
		}
		return payTranHeader;
	}

	public List<PayTranHeader> getAllPayTranHeaders() {
		return payTranHeaderMapper.search(null);
	}

	public Page<PayTranHeader> getAllPayTranHeaders(Page<PayTranHeader> page) {
		List<PayTranHeader> payTranHeaders = payTranHeaderMapper.search(page.getSearchMap(), page.getRowBounds());
		int count = payTranHeaderMapper.count(page.getSearchMap());
		page.setContent(payTranHeaders);
		page.setTotal(count);
		return page;
	}

	public void save(PayTranHeader payTranHeader) {
		payTranHeader.setCreateBy(getCurrentUser());
		payTranHeader.setCreateDate(new Date());
		payTranHeaderMapper.save(payTranHeader);
	}

	@Transactional(rollbackFor = Exception.class)
	public void save(PayTranHeader payTranHeader, String[] details, String[] attachments) throws Exception {
		payTranHeader.setTranDate(new Date());
		setTotalAmount(payTranHeader, details);
		save(payTranHeader);
		// save detail
		dealDetailAndAttachment(payTranHeader, details, attachments);
		// 第一封给财务审批 check/reject 邮件
		processService.noticeFinance(payTranHeader.getTranNum());
	}

	private void setTotalAmount(PayTranHeader payTranHeader, String[] details) {
		BigDecimal totamAmount = new BigDecimal(0);
		for (String detail : details) {
			String[] detailColums = detail.split("#");
			double amount = StringUtils.isNoneBlank(detailColums[2]) ? Double.parseDouble(detailColums[2]) : 0d;
			totamAmount = totamAmount.add(new BigDecimal(amount));
		}
		payTranHeader.setTotalAmount(totamAmount.doubleValue());
	}

	private void dealDetailAndAttachment(PayTranHeader payTranHeader, String[] details, String[] attachments) {
		final List<CurrMaster> currMasters = currMasterService.getAllCurrMaster();
		for (String detail : details) {
			PayTranDetail payTranDetail = new PayTranDetail();
			String[] detailColums = detail.split("#");
			// bdUserName+"#"+payCode+"#"+amount+#amountINRMB
			payTranDetail.setTranNum(payTranHeader.getTranNum());
			payTranDetail.setCreateBy(getCurrentUser());
			payTranDetail.setCreateDate(new Date());
			payTranDetail.setBdUserName(detailColums[0]);
			payTranDetail.setPayCode(Long.parseLong(detailColums[1]));
			payTranDetail.setAmount(StringUtils.isNoneBlank(detailColums[2]) ? Double.parseDouble(detailColums[2]) : 0d);
			payTranDetail.setAmountInRMB(CommonUtil.formatNumber( CommonUtil.transferMontyToRMB(currMasters,payTranHeader.getCurrency(), payTranDetail.getAmount()),3));
			// 获取百度用户信息
			CustMaster custMaster = custMasterMapper.findByCustUsername(payTranDetail.getBdUserName());
			if(custMaster!=null){
				Double manageFeePercent = Double.parseDouble(StringUtils.isNotBlank(custMaster.getMgtFeePercent())?custMaster.getMgtFeePercent():"0");
				Double rewardsPercent =Double.parseDouble(StringUtils.isNotBlank(custMaster.getRewardsPercent())?custMaster.getRewardsPercent():"0");
				payTranDetail.setMgtFee(manageFeePercent);
				payTranDetail.setRewards(rewardsPercent);
				if(payTranDetail.getPayCode()==1){
					// 如果是深圳, 否则是香港
					if(StringUtils.isNotBlank(custMaster.getFin_email())&&"sz".equalsIgnoreCase(custMaster.getFin_email().substring(0, 2))){
						// 入账/汇率/(1+管理费)*(1+续费返点)
						payTranDetail.setAdditionAmount(  CommonUtil.formatNumber(CommonUtil.transferMontyToBigDecimalRMB(currMasters,payTranHeader.getCurrency(), payTranDetail.getAmount())
								.divide(new BigDecimal(1+manageFeePercent),2, BigDecimal.ROUND_HALF_UP)//汇率
								.multiply(new BigDecimal(1+rewardsPercent)).doubleValue(),2)
								);
						
					}else{
						// 入账/汇率/(1+管理费)/(1-续费返点)
						payTranDetail.setAdditionAmount( CommonUtil.formatNumber(  CommonUtil.transferMontyToBigDecimalRMB(currMasters,payTranHeader.getCurrency(), payTranDetail.getAmount())
								.divide(new BigDecimal(1+manageFeePercent),2, BigDecimal.ROUND_HALF_UP)//汇率
								.divide(new BigDecimal(1-rewardsPercent),2, BigDecimal.ROUND_HALF_UP).doubleValue(),2)
								);
					}
				}else{
					payTranDetail.setAdditionAmount(0);
				}
			}else{
				if(payTranDetail.getPayCode()==1){
					payTranDetail.setAdditionAmount( CommonUtil.formatNumber( CommonUtil.transferMontyToBigDecimalRMB(currMasters,payTranHeader.getCurrency(),payTranDetail.getAmount()).doubleValue(),2));
				}else{
					payTranDetail.setAdditionAmount(0);
				}
			}
			
			payTranDetailMapper.save(payTranDetail);
		}

		// save attachment
		if (attachments != null && attachments.length > 0) {
			for (String attachmentId : attachments) {
				PayTranAttachement tmpAtta = payTranAttachmentMapper.getById(Long.parseLong(attachmentId));
				tmpAtta.setTranNum(payTranHeader.getTranNum());
				payTranAttachmentMapper.update(tmpAtta);
			}
		}
	}

	public void update(PayTranHeader payTranHeader) {
		payTranHeader.setUpdateBy(getCurrentUser());
		payTranHeader.setUpdateDate(new Date());
		payTranHeaderMapper.update(payTranHeader);
	}

	public void update(PayTranHeader payTranHeader, String[] details, String[] attachements) {
		update(payTranHeader);
		List<PayTranDetail> oldDetails = payTranDetailMapper.get(payTranHeader.getTranNum());
		for (PayTranDetail oldDetail : oldDetails) {
			payTranDetailMapper.delete(oldDetail.getTranNumDetail());
		}
		// 添加新的
		dealDetailAndAttachment(payTranHeader, details, attachements);
	}

	public void delete(Long tranNum) {
		List<PayTranDetail> details = payTranDetailMapper.get(tranNum);
		for (PayTranDetail detail : details) {
			payTranDetailMapper.delete(detail.getTranNumDetail());
		}
		List<PayTranAttachement> attachments = payTranAttachmentMapper.get(tranNum);
		for (PayTranAttachement attach : attachments) {
			payTranAttachmentMapper.delete(attach.getAttachmentId());
		}
		payTranHeaderMapper.delete(tranNum);
	}

	private String getCurrentUser() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user != null ? user.id +"": "-";
	}
	
	private String getCurrentUserName() {
	   ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	   return user != null ? user.getName() +"": "-";
	}
	
	public List<CustPaymentInfo> findCustPaymentInfos(Map<String, Object> parms) {
		return (List<CustPaymentInfo>) payTranHeaderMapper.findCustPaymentInfos(parms);
	}

	public Page<CustPaymentInfo> findCustPaymentInfos(Page<CustPaymentInfo> page) {
		List<CustPaymentInfo> list = payTranHeaderMapper.findCustPaymentInfos(page.getSearchMap(), page.getRowBounds());
		int count = payTranHeaderMapper.countCustPaymentInfos(page.getSearchMap());
		page.setContent(list);
		page.setTotal(count);
		return page;
	}
	
	public void pass(Long tranNum,Character status) throws Exception{
		Character nextStatus = null;
		switch (status) {
		case '1':
			nextStatus = CommonUtil.STATUS_CHECK;
			break;
		case '3':
			nextStatus = CommonUtil.STATUS_FINANCE_CONFIRM;
			break;
		case '4':
			nextStatus = CommonUtil.STATUS_DONE;
			break;
		}
		if(nextStatus!=null){
			PayTranHeader payTranHeader =  payTranHeaderMapper.get(tranNum);
			payTranHeader.setStatus(nextStatus);
			payTranHeader.setUpdateDate(new Date());
			payTranHeader.setUpdateBy(getCurrentUser());
			payTranHeaderMapper.update(payTranHeader);
			// 新增一条process记录
			 Process process = new Process();
			 process.setPayTranNum( payTranHeader.getTranNum() );
			 process.setStatus( nextStatus );
			 process.setCreateDate( new Date() );
			 process.setUpdateDate( new Date() );
			 process.setUpdateBy( getCurrentUserName() );
			 process.setIsUpdated( '1' );
			 processService.save( process );
			// 发送当前步骤的邮件
			if(status==CommonUtil.STATUS_NEW){
				// 当前是new->check
				processService.check(null, tranNum);
			}else if(status==CommonUtil.STATUS_CHECK){
				processService.financeConfirm(null, tranNum);
			}else if(status==CommonUtil.STATUS_FINANCE_CONFIRM){
				processService.opsConfirm(null, tranNum);
			}
		}
	}
	
	public void reject(Long tranNum,String description) throws Exception{
		PayTranHeader payTranHeaderDB = payTranHeaderMapper.get(tranNum);
		payTranHeaderDB.setDescription(description);
		payTranHeaderDB.setStatus(CommonUtil.STATUS_REJECT);
		payTranHeaderDB.setUpdateDate(new Date());
		payTranHeaderDB.setUpdateBy(getCurrentUser());
		payTranHeaderMapper.update(payTranHeaderDB);
		processService.reject(null, tranNum, description);
	}

}
