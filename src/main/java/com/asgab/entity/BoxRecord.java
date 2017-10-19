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
    private Date createTime;
    private Integer status;

    public BoxRecord() {
    }

    public BoxRecord(Long userId, Long serviceId, Integer type, Date appointmentTime) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.type = type;
        this.appointmentTime = appointmentTime;
    }

    public BoxRecord(Long userId, Long serviceId, Integer type, Date appointmentTime, BigDecimal cost) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.type = type;
        this.appointmentTime = appointmentTime;
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
}