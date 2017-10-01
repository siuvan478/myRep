package com.asgab.repository;

import com.asgab.entity.Address;
import com.asgab.repository.mybatis.MyBatisRepository;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface AddressMapper {

    List<Address> search(Map<String, Object> parameters);

    List<Address> search(Map<String, Object> parameters, RowBounds rowBounds);

    int count(Map<String, Object> map);

    Address get(Long id);

    Long save(Address area);

    void update(Address area);

    void delete(Long id);
}