package com.javatechie.crud.example.controller;

import com.javatechie.crud.example.entity.OrdersEntity;
import com.javatechie.crud.example.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/times")
    List<String> getTimes(@RequestParam(name="value", required = false, defaultValue = "default") String value){

        log.info(value);
/*
        List<OrdersEntity> ret = repository.findAll();
        log.info("Found " + ret.size() + " orders");

        for(OrdersEntity o : ret){
            if (o.getDate().toString().equals(date)){
                //TODO
            }
        }
*/
        return new ArrayList<>(Arrays.asList("A","B","C"));
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
