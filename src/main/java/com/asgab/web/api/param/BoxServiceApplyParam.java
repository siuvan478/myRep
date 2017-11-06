package com.asgab.web.api.param;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 文件柜服务申请请求参数
 */
public class BoxServiceApplyParam implements Serializable {
    private static final long serialVersionUID = 1073569695105136553L;

    private Long serviceId;//服务ID
    private Date appointmentTime;//预约时间
    private Integer applyType;//申请类型 1=预约收件 2=预约提货
    private BigDecimal cost;//预约费用

    public BoxServiceApplyParam() {
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
