package com.javatechie.crud.example.repository;


import com.javatechie.crud.example.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrdersEntity, Integer> {
}
