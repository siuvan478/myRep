package com.asgab.core.mail;

import com.asgab.util.SpringHolder;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月29日 下午 5:06
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
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