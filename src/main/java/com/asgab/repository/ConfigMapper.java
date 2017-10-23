package com.asgab.repository;

import com.asgab.entity.Config;
import com.asgab.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface ConfigMapper {

    Config get();

    void update(Config config);
}
