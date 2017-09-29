package com.asgab.entity;

import java.util.Date;

public class Mail {
	private Long id;
	private String receiver;
	private String subject;
	private Long paytranNum;
	private Long processId;
	private String urlConfirm;
	private String urlReject;
	private String template;
	private Date createDate;
	private Date sendDate;
	private Character status;
	
	// for app
	private String description;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public String getUrlConfirm() {
		return urlConfirm;
	}
	public void setUrlConfirm(String urlConfirm) {
		this.urlConfirm = urlConfirm;
	}
	public String getUrlReject() {
		return urlReject;
	}
	public void setUrlReject(String urlReject) {
		this.urlReject = urlReject;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Character getStatus() {
		return status;
	}
	public void setStatus(Character status) {
		this.status = status;
	}
	public Long getPaytranNum() {
		return paytranNum;
	}
	public void setPaytranNum(Long paytranNum) {
		this.paytranNum = paytranNum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
