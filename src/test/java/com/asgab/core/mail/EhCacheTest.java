package com.asgab.core.mail;

import com.asgab.util.SpringHolder;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class EhCacheTest {

    @Test
    public void test(){
        CacheManager ehcache = (CacheManager)SpringHolder.getBean("shiroEhcacheManager");
        Cache shiroCache = ehcache.getCache("shiroCache");
        shiroCache.put("295636011@qq.com","9527");
        System.out.println(shiroCache.get("295636011@qq.com"));
    }
}