package com.javatechie.crud.example.controller;

import com.javatechie.crud.example.entity.OrdersEntity;
import com.javatechie.crud.example.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/orders/all")
    List<OrdersEntity> all() {

        log.info("GetMapping /orders");

        List<OrdersEntity> ret = repository.findAll();
        log.info("Found " + ret.size() + " orders");

        for(OrdersEntity o : ret){
            log.info("Order: " + o);
        }

        return ret;
    }

    @PostMapping("/orders")
    OrdersEntity getNewOrder(@RequestBody OrdersEntity newOrder) {
        log.info("PostMapping /orders");
        log.info("newOrder " + newOrder);

        return repository.save(newOrder);
    }

/*
    @PutMapping("/orders/{id}")
    OrdersEntity replaceOrder(@RequestBody OrdersEntity newOrder, @PathVariable Integer id){
        log.info("PutMapping /orders/{id}");
        log.info("Id: " + id);


        OrdersEntity ret = repository.findById(id)
                .map(order -> {
                    order.setPrice(newOrder.getPrice());
                    order.setDate(newOrder.getDate());
                    order.setDescription(newOrder.getDescription());
                    return repository.save(order);
                })
                .orElseGet(() -> {
                    newOrder.setId(id);
                    return repository.save(newOrder);
                });



        log.info("updated or created order: " + ret);


        return ret;
    }
*/
    /*
    @DeleteMapping("/orders/{id}")
    void deleteOrderById(@PathVariable Integer id) {
        log.info("DeleteMapping /orders/{id}");
        log.info("Id: " + id);

        repository.deleteById(id);
    }
*/
}
