package com.qf.shop.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"spring-provider.xml"});
        ctx.start();
        System.in.read();

    }

}
