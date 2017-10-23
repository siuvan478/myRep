package com.asgab.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 配置
 */
public class Config implements Serializable {

    private static final long serialVersionUID = -5385024172623947757L;
    private BigDecimal commonFee;
    private BigDecimal discountFee;
    private Integer number;

    public Config() {
    }

    public BigDecimal getCommonFee() {
        return commonFee;
    }

    public void setCommonFee(BigDecimal commonFee) {
        this.commonFee = commonFee;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
