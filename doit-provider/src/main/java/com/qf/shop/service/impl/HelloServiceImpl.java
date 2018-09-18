package com.qf.shop.service.impl;

import com.qf.shop.service.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String say(String name) {

        return "Hello"+name;
    }
}
