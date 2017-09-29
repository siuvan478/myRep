package com.asgab.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.format.annotation.DateTimeFormat;

import com.asgab.util.CommonUtil;

public class PayTranHeader {
	private Long tranNum;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  
	private Date tranDate;
	private String currency;
	private String email;
	private double totalAmount;
	private String imageSysName;
	private String imageName;
	private Character status;
	private String remarks;
	private String description;
	private int deleted;
	private Date createDate;
	private String createBy;
	private Date updateDate;
	private String updateBy;

	private List<PayTranDetail> payTranDetails = new ArrayList<PayTranDetail>();
	private List<PayTranAttachement> payTranAttachements = new ArrayList<PayTranAttachement>();
	private List<CurrMaster> currMasters = new ArrayList<CurrMaster>();
	
	private Map<String,String> currencys = new TreeMap<String, String>();
	private Map<Character,String> statuses = new TreeMap<Character,String>();
	private Map<Long,String> payCodes = new TreeMap<Long,String>();
	
	public Long getTranNum() {
		return tranNum;
	}

	public void setTranNum(Long tranNum) {
		this.tranNum = tranNum;
	}

	public Date getTranDate() {
		return tranDate;
	}
	
	public String getTranDateFormat(){
		return CommonUtil.formatDate(getTranDate());
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	
	public String getDecodedCurrency(){
		String tmp = currencys.get(currency);
		return  tmp==null?"":tmp;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getImageSysName() {
		return imageSysName;
	}

	public void setImageSysName(String imageSysName) {
		this.imageSysName = imageSysName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}


	public String getDecodedStatus(){
		String tmp = statuses.get(status);
		return tmp==null?"":tmp;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<PayTranDetail> getPayTranDetails() {
		return payTranDetails;
	}

	public void setPayTranDetails(List<PayTranDetail> payTranDetails) {
		this.payTranDetails = payTranDetails;
	}

	public List<PayTranAttachement> getPayTranAttachements() {
		return payTranAttachements;
	}

	public void setPayTranAttachements(List<PayTranAttachement> payTranAttachements) {
		this.payTranAttachements = payTranAttachements;
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
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Character getStatus() {
		return status;
	}
	
	public String getStatusStr(){
		return String.valueOf(status);
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}


	public Map<String, String> getCurrencys() {
		return currencys;
	}

	public void setCurrencys(Map<String, String> currencys) {
		this.currencys = currencys;
	}

	public Map<Character, String> getStatuses() {
		return statuses;
	}

	public void setStatuses(Map<Character, String> statuses) {
		this.statuses = statuses;
	}

	public Map<Long, String> getPayCodes() {
		return payCodes;
	}

	public void setPayCodes(Map<Long, String> payCodes) {
		this.payCodes = payCodes;
	}

	public List<CurrMaster> getCurrMasters() {
		return currMasters;
	}

	public void setCurrMasters(List<CurrMaster> currMasters) {
		this.currMasters = currMasters;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
