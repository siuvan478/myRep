package com.asgab.service;

import org.apache.shiro.cache.ehcache.EhCache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月29日 下午 5:46
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
@Component
public class EhCacheService implements InitializingBean {

    @Resource
    private EhCacheManager shiroEhcacheManager;
    private EhCache cache;

    public EhCache getCache() {
        return cache;
    }

    public void setCache(EhCache cache) {
        this.cache = cache;
    }

    /**
     * 将一个对象放入缓存
     */
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    /**
     * 将一个对象移出缓存
     */
    public void remove(String key) {
        cache.remove(key);
    }

    /**
     * 从缓存中获得一个对象
     */
    public Object get(String key) {
        return cache.get(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setCache((EhCache) shiroEhcacheManager.getCache("shiroCache"));
    }
}