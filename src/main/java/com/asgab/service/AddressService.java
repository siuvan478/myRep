package com.asgab.service;

import com.asgab.core.pagination.Page;
import com.asgab.entity.Address;
import com.asgab.repository.AddressMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Component
@Transactional
public class AddressService {

    @Resource
    private AddressMapper addressMapper;

    public Page<Address> pageQuery(Page<Address> page) {
        List<Address> list = addressMapper.search(page.getSearchMap(), page.getRowBounds());
        int count = addressMapper.count(page.getSearchMap());
        page.setContent(list);
        page.setTotal(count);
        return page;
    }

    public Address get(Long id) {
        return addressMapper.get(id);
    }

    public Long save(Address address) {
        return addressMapper.save(address);
    }

    public void update(Address address) {
        addressMapper.update(address);
    }

    public void delete(Long id) {
        addressMapper.delete(id);
    }
}