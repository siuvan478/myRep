package com.asgab.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.asgab.util.Cryptogram;

public class Process {
	private Long processId;
	private Long payTranNum;
	private String randomKey;
	private Character status;
	private String description;
	private String remarks;
	private Date createDate;
	private Date updateDate;
	private String updateBy;
	private String randomIdentification;
	private Character isUpdated='0';
	
	private String encodedId;
	
	private Map<Character,String> statuses = new HashMap<Character,String>();
	
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public Long getPayTranNum() {
		return payTranNum;
	}
	public void setPayTranNum(Long payTranNum) {
		this.payTranNum = payTranNum;
	}
	public String getRandomKey() {
		return randomKey;
	}
	public void setRandomKey(String randomKey) {
		this.randomKey = randomKey;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public String getDecodedStatus(){
		String tmp = statuses.get(status);
		return tmp==null?"":tmp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getEncodedProcessId() {
		return Cryptogram.encodeId(processId+"");
	}

	public Character getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Character isUpdated) {
		this.isUpdated = isUpdated;
	}

	public Map<Character, String> getStatuses() {
		return statuses;
	}

	public void setStatuses(Map<Character, String> statuses) {
		this.statuses = statuses;
	}

	public void setEncodedId(String encodedId) {
		this.encodedId = encodedId;
	}

	public String getEncodedId() {
		return encodedId;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getRandomIdentification() {
		return randomIdentification;
	}
	public void setRandomIdentification(String randomIdentification) {
		this.randomIdentification = randomIdentification;
	}
	
}
