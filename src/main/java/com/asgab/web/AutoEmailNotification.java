package com.asgab.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.asgab.core.mail.MailUtil;
import com.asgab.entity.CurrMaster;
import com.asgab.entity.CustMaster;
import com.asgab.entity.Mail;
import com.asgab.entity.PayTranAttachement;
import com.asgab.entity.PayTranDetail;
import com.asgab.entity.PayTranHeader;
import com.asgab.util.CommonUtil;
import com.asgab.util.MoneyToChinese;

public class AutoEmailNotification {

	public final static String EMAIL_TEMPLATE_NAME_1 = "example1.ftl";

	public final static String EMAIL_TEMPLATE_NAME_2 = "example2.ftl";

	public final static String EMAIL_TEMPLATE_NAME_3 = "example3.ftl";

	public final static String EMAIL_TEMPLATE_NAME_4 = "example4.ftl";
	
	// 发送给创建paytran的email
	public final static String EMAIL_TEMPLATE_NAME_5 = "example5.ftl";
	
	public final static String EMAIL_TEMPLATE_NAME_REJECT = "reject.ftl";

	/***
	 * send1 发送邮件 > Finance
	 *
	 * @param custMaster
	 *            客户信息
	 * @param header
	 *            交易信息
	 * @param parms
	 *            参数集合
	 * @return success
	 */
	public static void send1( Map<String,CustMaster> custMasterMap,PayTranHeader header,Map<String, Object> parms) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		// 账单备注
		map.put("paymentRemark", getValue(header.getRemarks()));

		// 交易号
		map.put("tradeNo", String.valueOf(header.getTranNum()));
		// 收据上传日期
		map.put("uploadDate", header.getTranDateFormat());
		// 确认到账 / 拒绝到账 连接
		map.put("confirmArrivalURL", getValue(parms.get("confirmArrivalURL")));
		map.put("rejectArrivalURL", getValue(parms.get("rejectArrivalURL")));

		// 币种
		map.put("currency", getDecodedCurrency(header.getCurrMasters(), header.getCurrency()));

		map.put("totalAmountInRMB", CommonUtil.transferMontyToRMB(header.getCurrMasters(),header.getCurrency(), header.getTotalAmount()));
		map.put("custUserList", getCustUserList(header,custMasterMap,String.valueOf(parms.get("receiver"))));
		MailUtil.sendMailAndFileByTemplate(getValue(parms.get("receiver")),getValue(parms.get("subject")),getFilePaths(header), map, EMAIL_TEMPLATE_NAME_1);
	}

	/***
	 * send2 发送邮件 > Finance
	 * 
	 * @param custMaster
	 *            客户信息
	 * @param header
	 *            交易信息
	 * @param parms
	 *            参数集合
	 * @return success
	 */
	public static void send2(Map<String,CustMaster> custMasterMap, PayTranHeader header,
			Map<String, Object> parms) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		// 账单备注
		map.put("paymentRemark", getValue(header.getRemarks()));
		// 交易号
		map.put("tradeNo", String.valueOf(header.getTranNum()));
		// 收据上传日期
		map.put("uploadDate", header.getTranDateFormat());
		// 财务确认查账日期
		map.put("finConfirmAuditDate",getValue(parms.get("finConfirmAuditDate")));
		// 财务查账人员
		map.put("finAuditBy", getValue(parms.get("finAuditBy")));
		// 确认完成打款
		map.put("confirmPayTranURL", getValue(parms.get("confirmPayTranURL")));

		// 币种
		map.put("currency", getDecodedCurrency(header.getCurrMasters(), header.getCurrency()));
		map.put("totalAmountInRMB", CommonUtil.transferMontyToRMB(header.getCurrMasters(),header.getCurrency(), header.getTotalAmount()));
		map.put("custUserList", getCustUserList(header,custMasterMap,String.valueOf(parms.get("receiver"))));

		MailUtil.sendMailAndFileByTemplate(getValue(parms.get("receiver")),getValue(parms.get("subject")),getFilePaths(header) ,map, EMAIL_TEMPLATE_NAME_2);
		
	}

	/***
	 * send3 发送邮件 > Ops
	 * 
	 * @param custMaster
	 *            客户信息
	 * @param header
	 *            交易信息
	 * @return success
	 */
	public static void send3(Map<String,CustMaster> custMasterMap, PayTranHeader header,
			Map<String, Object> parms) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		// 账单备注
		map.put("paymentRemark", getValue(header.getRemarks()));
		// 交易号
		map.put("tradeNo", String.valueOf(header.getTranNum()));
		// 财务确认收款日期
		map.put("finConfirmReceivableDate",getValue(parms.get("finConfirmReceivableDate")));
		// 财务确认打款日期
		map.put("finPayTranDate", getValue(parms.get("finPayTranDate")));
		// 财务确收人员
		map.put("finConfirmReceivableBy",
				getValue(parms.get("finConfirmReceivableBy")));
		// 财务打款人员
		map.put("finPayTranBy", getValue(parms.get("finPayTranBy")));
		
		// 确认入账至客户用户名连接
		map.put("confirmCustArrivalURL",getValue(parms.get("confirmCustArrivalURL")));

		// 币种
		map.put("currency", getDecodedCurrency(header.getCurrMasters(), header.getCurrency()));
		map.put("totalAmountInRMB", CommonUtil.transferMontyToRMB(header.getCurrMasters(),header.getCurrency(), header.getTotalAmount()));
		map.put("custUserList", getCustUserList(header,custMasterMap,String.valueOf(parms.get("receiver"))));
		map.put("annualInfoList", getAnnualInfoList(header, custMasterMap));

		MailUtil.sendMailAndFileByTemplate(getValue(parms.get("receiver")),getValue(parms.get("subject")), getFilePaths(header),map, EMAIL_TEMPLATE_NAME_3);

	}

	/***
	 * send4 发送邮件 > AM & Sales
	 * 
	 * @param custMaster
	 *            客户信息
	 * @param header
	 *            交易信息
	 * @param parms
	 *            参数集合
	 * @return success
	 */
	public static void send4(Map<String,CustMaster> custMasterMap, PayTranHeader header,
			Map<String, Object> parms) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		// 账单备注
		map.put("paymentRemark", getValue(header.getRemarks()));
		// 交易号
		map.put("tradeNo", String.valueOf(header.getTranNum()));
		// 财务确认收款日期
		map.put("finConfirmReceivableDate",getValue(parms.get("finConfirmReceivableDate")));
		// 财务确认打款日期
		map.put("finPayTranDate", getValue(parms.get("finPayTranDate")));
		// 财务确收人员
		map.put("finConfirmReceivableBy",
				getValue(parms.get("finConfirmReceivableBy")));
		// 财务打款人员
		map.put("finPayTranBy", getValue(parms.get("finPayTranBy")));

		// 币种
		map.put("currency", getDecodedCurrency(header.getCurrMasters(), header.getCurrency()));
		map.put("totalAmountInRMB", CommonUtil.transferMontyToRMB(header.getCurrMasters(),header.getCurrency(), header.getTotalAmount()));
		map.put("custUserList", getCustUserList(header,custMasterMap,String.valueOf(parms.get("receiver"))));
		map.put("annualInfoList", getAnnualInfoList(header, custMasterMap));

		MailUtil.sendMailAndFileByTemplate(getValue(parms.get("receiver")),getValue(parms.get("subject")),getFilePaths(header) ,map, EMAIL_TEMPLATE_NAME_4);
	}
	
	/**
	 * 发送邮件给paytran email
	 * @param custMasterMap
	 * @param header
	 * @param parms
	 * @throws Exception
	 */
	public static void send5(PayTranHeader header,Mail mail) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		// 交易号
		map.put("tradeNo", String.valueOf(header.getTranNum()));
		// 数据上传时间
		map.put("uploadDate", CommonUtil.formatDate(header.getCreateDate()));
		// 确认日期
		map.put("finPayTranDate", CommonUtil.formatDate(header.getUpdateDate()));
		// 币种
		map.put("currency", getDecodedCurrency(header.getCurrMasters(), header.getCurrency()));
		//收据总计
		map.put("totalAmountInRMB", CommonUtil.transferMontyToRMB(header.getCurrMasters(), header.getCurrency(), header.getTotalAmount()));
		//交易备注
		map.put("paymentRemark", header.getRemarks());
		List<Map<String,Object>> details = new ArrayList<Map<String,Object>>();
		for(PayTranDetail detail:header.getPayTranDetails()){
			Map<String,Object> detailMap = new HashMap<String,Object>();
			detailMap.put("bdUserName", detail.getBdUserName());
			detailMap.put("payCode", detail.getDecodePayCode());
			detailMap.put("amount", detail.getAmount());
			detailMap.put("amountInRMB", detail.getAmountInRMB());
			details.add(detailMap);
		}
		map.put("details", details);

		MailUtil.sendMailByTemplate(mail.getReceiver(),mail.getSubject(),map, EMAIL_TEMPLATE_NAME_5);
	}

	/***
	 * 财务拒绝到账
	 * 
	 * @param receiver
	 *            收件人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @return
	 */
	public static void send_reject(Map<String,Object> map) {
		String receiver = String.valueOf(map.get("receiver"));
		String subject = String.valueOf(map.get("subject"));
		try {
			MailUtil.sendMailByTemplate(receiver,subject, map, EMAIL_TEMPLATE_NAME_REJECT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 获取返点金额 = 入账金额 * 返点率
	 * 
	 * @param amount
	 *            入账金额
	 * @param rebate
	 *            返点率
	 * @return 返点金额
	 */
	private static String getRebateAmount(double amount, double rebate) {
		try {
			DecimalFormat df = new DecimalFormat("0.000");
			return df.format(new BigDecimal(amount).multiply(new BigDecimal(rebate)));
		} catch (NumberFormatException e) {
			return "";
		}
	}
	

	/***
	 * 返回一个字符串
	 * 
	 * @param object
	 * @return
	 */
	private static String getValue(Object object) {
		if (object == null)
			return "";

		if (object instanceof String)
			return object.toString();

		if (object instanceof Date)
			return CommonUtil.formatDate((Date) object, "yyyy-MM-dd HH:mm:ss");

		return "";
	}
	
	/**
	 * 公共的部分
	 */
	private static List<Map<String,String>> getCustUserList(PayTranHeader header,Map<String,CustMaster> custMasterMap,String receiver){
		List<Map<String,String>> custUserList = new ArrayList<Map<String,String>>();
		for(int i = 0 ; i < header.getPayTranDetails().size();i++){
			PayTranDetail detail = header.getPayTranDetails().get(i);
			String baiduUserName = detail.getBdUserName();
			double amount = detail.getAmount();
			// 每一个百度用户添加一列数据
			Map<String,String> custUserMap = new HashMap<String, String>();
			// 获取对于的百度客户信息
			CustMaster custMaster = custMasterMap.get(baiduUserName);
			boolean existCustMaster = custMaster!=null;
			// 客户用户名
			custUserMap.put("custUsername", baiduUserName);
			// 客户名称
			custUserMap.put("custName", existCustMaster?custMaster.getCustName():"");
			
			// 入账金额小写
			custUserMap.put("rzjeLowercase", String.valueOf(amount));
			// 入账金额大写
			custUserMap.put("rzjeCapital", MoneyToChinese.getMoneyString(amount));
			// 加款金额小写 
			custUserMap.put("jkjeLowercase", String.valueOf(detail.getAdditionAmount()));
			// 加款金额大写 
			custUserMap.put("jkjeCapital", MoneyToChinese.getMoneyString(detail.getAdditionAmount()));
			// 入账项目
			String item="";
			if(detail.getPayCode()==1){
				// 如果是加款
				item = "管理费 "+detail.getMgtFee()*100+"%";
			}else{
				item = detail.getDecodePayCode();
			}
			custUserMap.put("item", item);
			// 如果是年费
			if(detail.getPayCode()==2){
				// 返点率
				custUserMap.put("rebate", "0.0");
				// 返点金额
				custUserMap.put("rebateAmount","0.0");
			}else{
				// 返点率
				custUserMap.put("rebate", String.valueOf( detail.getRewards()*100));
				// 返点金额
				custUserMap.put("rebateAmount",getRebateAmount(amount, detail.getRewards()));
			}
			// 赠送金额
			custUserMap.put("giftAmount", "0.0");
			
			// 端口
			custUserMap.put("custPort", existCustMaster?custMaster.getCustPort():"");
			// 销售
			custUserMap.put("sales_contact", existCustMaster?custMaster.getSales_contact():"");
			// 客服
			custUserMap.put("custService", existCustMaster?custMaster.getAm_contact():"");
			// 代理商
			custUserMap.put("agent", existCustMaster?custMaster.getCustName():"");
			custUserList.add(custUserMap);
		}
		
		return custUserList;
	}
	

	/**
	 * 公共部分2  用于模板34
	 * @param header
	 * @param custMasterMap
	 * @return
	 */
	private static List<Map<String,String>> getAnnualInfoList(PayTranHeader header,Map<String,CustMaster> custMasterMap){
		List<Map<String,String>> annualInfoList = new ArrayList<Map<String,String>>();
		List<String> distinctBDUserNameList =  getDistinctCustUserName(header.getPayTranDetails());
		for(String bdUserName:distinctBDUserNameList){
			Map<String,String > tmpMap = new HashMap<String,String>();
			// 获取对于的百度客户信息
			CustMaster custMaster = custMasterMap.get(bdUserName);
			boolean existCustMaster = custMaster!=null; 
			// 公司名称
			tmpMap.put("custName", existCustMaster?custMaster.getCustName():"");
			// 收取年服务费时间
			tmpMap.put("annualSvcFeeDate", existCustMaster?CommonUtil.formatDate(
					custMaster.getAnnualSvcFeeDate(), "yyyy年MM月"):"");
			// 续费返点率
			tmpMap.put("rewardsPercent", existCustMaster?custMaster.getRewardsPercent():"");
			// 收取的年服务费
			tmpMap.put("annualSvcFee", existCustMaster?StringUtils.isNotBlank(custMaster.getAnnualSvcFee())?custMaster.getAnnualSvcFee():"":"");
			// 管理费率
			tmpMap.put("mgtFeePercent", existCustMaster?StringUtils.isNotBlank(custMaster.getMgtFeePercent())?custMaster.getMgtFeePercent():"":"");
			// 备注
			tmpMap.put("remark1",existCustMaster?StringUtils.isNotBlank(custMaster.getRemark1()) ? custMaster.getRemark1() : "":"");
			annualInfoList.add(tmpMap);
		}
		return annualInfoList;
	}
	
	private static List<String> getDistinctCustUserName(List<PayTranDetail> payTranDetails){
		Map<String,String> map = new HashMap<String, String>();
		for(PayTranDetail detail:payTranDetails){
			map.put(detail.getBdUserName(), detail.getBdUserName());
		}
		List<String> bdUserNameList = new ArrayList<String>();
		Set<Entry<String,String>> bdSet = map.entrySet();
		Iterator<Entry<String,String>> bdIterator = bdSet.iterator();
		while(bdIterator.hasNext()){
			bdUserNameList.add(bdIterator.next().getKey());
		}
		return bdUserNameList;
	}
	
	private static String getDecodedCurrency(List<CurrMaster> currMasters,String currency){
		for(CurrMaster currMaster:currMasters ){
			if(currMaster.getCurr_code().equalsIgnoreCase(currency)){
				return currMaster.getCurr_name();
			}
		}
		return "";
	}
	
	private static List<String> getFilePaths(PayTranHeader payTranHeader){
		List<String> filePaths = new ArrayList<String>();
		List<PayTranAttachement> attachments = payTranHeader.getPayTranAttachements();
		for(PayTranAttachement attachment:attachments){
			filePaths.add(attachment.getPath()+"/"+attachment.getFileName());
		}
		return filePaths;
	}
}
