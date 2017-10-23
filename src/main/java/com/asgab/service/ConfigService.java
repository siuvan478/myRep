package com.asgab.service;

import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.CacheKey;
import com.asgab.constants.GlobalConstants;
import com.asgab.entity.Config;
import com.asgab.repository.ConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component
@Transactional
public class ConfigService implements InitializingBean {

    @Resource
    private ConfigMapper configMapper;

    @Resource
    private JedisService jedisService;

    public Config get() {
        return configMapper.get();
    }

    public void update(Config config) {
        configMapper.update(config);
        afterPropertiesSet();
    }

    @Override
    public void afterPropertiesSet() {
        jedisService.delete(CacheKey.APPOINT_FEE_KEY);
        Config config = configMapper.get();
        if (config != null) {
            jedisService.set(CacheKey.APPOINT_FEE_KEY, JSONObject.toJSONString(config));
        }
    }

    public Config getConfigFromCache() {
        String jsonObject = jedisService.get(CacheKey.APPOINT_FEE_KEY);
        if (StringUtils.isNotBlank(jsonObject)) {
            return JSONObject.parseObject(jsonObject, Config.class);
        }
        return configMapper.get();
    }

    public Integer isFullCost(BigDecimal appointFee) {
        if (appointFee == null || appointFee.doubleValue() == 0) {
            return GlobalConstants.YesOrNo.NO;
        }
        Config config = this.getConfigFromCache();
        if (config == null) {
            return GlobalConstants.YesOrNo.NO;
        }
        return appointFee.equals(config.getCommonFee()) ? GlobalConstants.YesOrNo.YES : GlobalConstants.YesOrNo.NO;
    }

}
