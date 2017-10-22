package com.asgab.repository;

import com.asgab.entity.Order;
import com.asgab.repository.mybatis.MyBatisRepository;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 *
 * @author calvin
 */
@MyBatisRepository
public interface OrderMapper {

    List<Order> search(Map<String, Object> parameters);

    List<Order> search(Map<String, Object> parameters, RowBounds rowBounds);

    int count(Map<String, Object> map);

    Order get(Long id);

    void save(Order order);

    void update(Order order);
}
