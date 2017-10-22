package com.asgab.web.api.param;

import com.asgab.entity.BoxService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的服务
 */
public class MyBoxService extends UserInfo implements Serializable {
    private static final long serialVersionUID = -1900566494954652838L;

    /**
     * 我的服务列表
     */
    private List<BoxService> boxServices = new ArrayList<>();

    public List<BoxService> getBoxServices() {
        return boxServices;
    }

    public void setBoxServices(List<BoxService> boxServices) {
        this.boxServices = boxServices;
    }
}
