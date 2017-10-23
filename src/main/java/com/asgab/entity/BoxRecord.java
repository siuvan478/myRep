package com.asgab.entity;


import java.math.BigDecimal;
import java.util.Date;

/**
 * 存/取记录
 */
public class BoxRecord {

    private Long id;
    private Long userId;
    private Long serviceId;
    private Integer type;
    private Date appointmentTime;
    private BigDecimal cost;
    private Integer fullCost;
    private Date createTime;
    private Integer status;
    private String picture1;
    private String picture2;
    private String picture3;
    private String picture4;

    public BoxRecord() {
    }

    public BoxRecord(Long userId, Long serviceId, Integer type, Date appointmentTime) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.type = type;
        this.appointmentTime = appointmentTime;
    }

    public BoxRecord(Long userId, Long serviceId, Integer type, Date appointmentTime, BigDecimal cost) {
        this(userId, serviceId, type, appointmentTime);
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getFullCost() {
        return fullCost;
    }

    public void setFullCost(Integer fullCost) {
        this.fullCost = fullCost;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    public String getPicture4() {
        return picture4;
    }

    public void setPicture4(String picture4) {
        this.picture4 = picture4;
    }
}