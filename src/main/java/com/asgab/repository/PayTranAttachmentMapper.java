package com.asgab.repository;

import java.util.List;

import com.asgab.entity.PayTranAttachement;
import com.asgab.repository.mybatis.MyBatisRepository;

@MyBatisRepository
public interface PayTranAttachmentMapper {

	List<PayTranAttachement> get(Long tranNum);
	
	PayTranAttachement getById(Long attachmentId);

	void save(PayTranAttachement payTranAttachement);

	void delete(Long attachmentId);
	
	void update(PayTranAttachement payTranAttachement);

}
