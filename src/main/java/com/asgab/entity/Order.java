package com.asgab.entity;

import com.asgab.constants.GlobalConstants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 5177031418711704992L;
    private Long id;
    private Long userId;
    private String orderNo;
    private Long addressId;
    private Long productId;
    private Long scaleId;
    private Integer cycle;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Date orderTime;
    private Date effectiveTime;
    private Integer status;
    private Long callbackId;
    private String remark;
    private String belongNo;

    //for app
    private String productName;
    private String scaleName;
    private String[] belongNoArray;

    public Order() {
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getScaleId() {
        return scaleId;
    }

    public void setScaleId(Long scaleId) {
        this.scaleId = scaleId;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(Long callbackId) {
        this.callbackId = callbackId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getScaleName() {
        return scaleName;
    }

    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

    public String getLabelClass() {
        return GlobalConstants.OrderStatus.getLabelClass(this.getStatus());
    }

    public String getBelongNo() {
        return belongNo;
    }

    public void setBelongNo(String belongNo) {
        this.belongNo = belongNo;
    }

    public String[] getBelongNoArray() {
        return belongNoArray;
    }

    public void setBelongNoArray(String[] belongNoArray) {
        this.belongNoArray = belongNoArray;
    }
}