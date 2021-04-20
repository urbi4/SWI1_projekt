package com.javatechie.crud.example.service;

import com.javatechie.crud.example.controller.OrderController;
import com.javatechie.crud.example.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderDatabaseLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderRepository repository;

    @Autowired
    OrderDatabaseLoader(OrderRepository repository) { this.repository = repository; }

    @Override
    public void run(String... args) {
        log.info("Preloading Order Data");

        //LocalDateTime now = LocalDateTime.now();
        // this.repository.save(new OrdersEntity(now,"Pneumatiky",1200));

        log.info("Numbers of loaded orders: " + this.repository.count());
    }
}