package com.asgab.service.paytran;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.asgab.entity.PayTranDetail;
import com.asgab.repository.PayTranDetailMapper;

@Component
@Transactional
public class PayTranDetailService {

	@Resource
	private PayTranDetailMapper payTranDetailMapper;
	
	public List<PayTranDetail> get(Long tranNum){
		return payTranDetailMapper.get(tranNum);
	}

	public void save(PayTranDetail payTranDetail){
		payTranDetailMapper.save(payTranDetail);
	}

	public void update(PayTranDetail payTranDetail){
		payTranDetailMapper.update(payTranDetail);
	}

	public void delete(Long tranNumDetail){
		payTranDetailMapper.delete(tranNumDetail);
	}
	
}
