package com.asgab.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PayTranDetail {
	private Long tranNumDetail;
	private Long tranNum;
	private Long payCode;
	private String bdUserName;
	private double amount;
	private double amountInRMB;
	private double rewards;
	private double mgtFee;
	private double additionAmount;
	private int deleted;
	private Date createDate;
	private String createBy;
	private Date updateDate;
	private String updateBy;
	
	private Map<Long,String> payCodes = new HashMap<Long,String>();
	
	public Long getTranNumDetail() {
		return tranNumDetail;
	}
	public void setTranNumDetail(Long tranNumDetail) {
		this.tranNumDetail = tranNumDetail;
	}
	public Long getTranNum() {
		return tranNum;
	}
	public void setTranNum(Long tranNum) {
		this.tranNum = tranNum;
	}

	public String getBdUserName() {
		return bdUserName;
	}
	public void setBdUserName(String bdUserName) {
		this.bdUserName = bdUserName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAmountInRMB() {
		return amountInRMB;
	}
	public void setAmountInRMB(double amountInRMB) {
		this.amountInRMB = amountInRMB;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getDecodePayCode(){
		String tmp = payCodes.get(payCode);
		return  tmp==null?"":tmp;
	}
	public double getAdditionAmount() {
		return additionAmount;
	}
	public void setAdditionAmount(double additionAmount) {
		this.additionAmount = additionAmount;
	}
	public double getRewards() {
		return rewards;
	}
	public void setRewards(double rewards) {
		this.rewards = rewards;
	}
	public double getMgtFee() {
		return mgtFee;
	}
	public void setMgtFee(double mgtFee) {
		this.mgtFee = mgtFee;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Long getPayCode() {
		return payCode;
	}
	public void setPayCode(Long payCode) {
		this.payCode = payCode;
	}
	public Map<Long, String> getPayCodes() {
		return payCodes;
	}
	public void setPayCodes(Map<Long, String> payCodes) {
		this.payCodes = payCodes;
	}
	
}
