package com.qf.shop.service.impl;

import com.qf.shop.service.DemoService;

public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {

        return "Hello"+name;
    }
}
