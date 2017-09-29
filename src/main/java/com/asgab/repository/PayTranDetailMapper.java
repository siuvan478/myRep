package com.asgab.repository;

import java.util.List;
import java.util.Map;

import com.asgab.entity.PayTranDetail;
import com.asgab.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface PayTranDetailMapper {

	List<PayTranDetail> get(Long tranNum);

	void save(PayTranDetail payTranDetail);

	void update(PayTranDetail payTranDetail);

	void delete(Long tranNumDetail);

	int count(Map<String, Object> map);
	
}
