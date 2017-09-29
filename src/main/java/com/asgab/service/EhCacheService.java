package com.asgab.service;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月29日 下午 5:46
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
@Component
public class EhCacheService {

    @Resource
    private EhCacheManager shiroEhcacheManager;

    public void put(String key, Object value) {
        Cache shiroCache = shiroEhcacheManager.getCache("shiroCache");
        shiroCache.put(key, value);
    }

    public Object get(String key) {
        Cache shiroCache = shiroEhcacheManager.getCache("shiroCache");
        return shiroCache.get(key);
    }
}