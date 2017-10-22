package com.asgab.service;

import com.asgab.constants.GlobalConstants;
import com.asgab.core.pagination.Page;
import com.asgab.entity.Address;
import com.asgab.repository.AddressMapper;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


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

    public void save(Address address) {
        addressMapper.save(address);
    }

    public void update(Address address) {
        addressMapper.update(address);
    }

    public void delete(Long id) {
        addressMapper.delete(id);
    }

    /**
     * 根据UserId获取收货地址
     *
     * @param userId
     * @return
     */
    public Address getUniqueAddressByUserId(Long userId) {
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("userId", userId);
        condition.put("status", GlobalConstants.Status.NORMAL);
        List<Address> list = addressMapper.search(condition);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}