package com.asgab.web.api.param;

import com.asgab.entity.BoxRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件柜详情
 */
public class BoxServiceDetails implements Serializable {
    private static final long serialVersionUID = 5810756116488076805L;

    private Long id;//文件柜ID；
    private String productName;//box名称
    private Integer flag;//文件柜状态
    private Date startTime;//有效期
    private Date endTime;//有效期
    private Integer status;//状态

    private List<BoxRecord> lastRecord = new ArrayList<>();

    public BoxServiceDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BoxRecord> getLastRecord() {
        return lastRecord;
    }

    public void setLastRecord(List<BoxRecord> lastRecord) {
        this.lastRecord = lastRecord;
    }
}
