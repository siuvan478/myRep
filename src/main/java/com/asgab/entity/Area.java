package com.asgab.entity;

import java.util.Map;
import java.util.TreeMap;

/**
 * 区域实体
 */
public class Area {

    private Long id;
    private String name;
    private String nameEN;
    private Long cityId;
    private Integer status;

    /**
     * for app
     */
    private Map<Character, String> cityMappings = new TreeMap<>();

    public Area() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<Character, String> getCityMappings() {
        return cityMappings;
    }

    public void setCityMappings(Map<Character, String> cityMappings) {
        this.cityMappings = cityMappings;
    }
}