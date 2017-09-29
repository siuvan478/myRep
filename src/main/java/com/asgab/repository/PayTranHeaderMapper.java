package com.asgab.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.asgab.entity.CustPaymentInfo;
import com.asgab.entity.PayTranHeader;
import com.asgab.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface PayTranHeaderMapper {

	PayTranHeader get(Long tranNum);

	List<PayTranHeader> search(Map<String, Object> parameters);

	List<PayTranHeader> search(Map<String, Object> parameters, RowBounds rowBounds);

	void save(PayTranHeader payTranHeader);

	void update(PayTranHeader payTranHeader);

	void delete(Long tranNum);

	int count(Map<String, Object> map);

	int countCustPaymentInfos(Map<String, Object> map);

	List<CustPaymentInfo> findCustPaymentInfos(Map<String, Object> parameters);

	List<CustPaymentInfo> findCustPaymentInfos(Map<String, Object> parameters, RowBounds rowBounds);

}
