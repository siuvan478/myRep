package com.asgab.service.api;


import com.asgab.util.LoginUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OrderWebService {

    public void submitOrder(){
        System.out.println(LoginUtil.getUserId());
    }
}