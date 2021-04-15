package com.javatechie.crud.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "orders_has_problem", schema = "mydb", catalog = "")
@IdClass(com.javatechie.crud.example.entity.OrdersHasProblemEntityPK.class)
public class OrdersHasProblemEntity {
    private int ordersId;
    private int problemId;

    @Id
    @Column(name = "Orders_id")
    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    @Id
    @Column(name = "Problem_id")
    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersHasProblemEntity that = (OrdersHasProblemEntity) o;

        if (ordersId != that.ordersId) return false;
        if (problemId != that.problemId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ordersId;
        result = 31 * result + problemId;
        return result;
    }
}
