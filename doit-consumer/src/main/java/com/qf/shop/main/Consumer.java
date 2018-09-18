package com.qf.shop.main;


import com.qf.shop.service.DemoService;
import com.qf.shop.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Consumer {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring-consumer.xml"});
        context.start();
        DemoService service = (DemoService)context.getBean("demoService");
        System.out.println(service.sayHello("杜明明"));
        System.out.println("===========================");
        HelloService helloService = (HelloService)context.getBean("helloService");
        System.out.println(helloService.say("方浩"));


    }
}
