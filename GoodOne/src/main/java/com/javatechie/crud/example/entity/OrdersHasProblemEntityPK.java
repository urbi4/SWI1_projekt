package com.javatechie.crud.example.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class OrdersHasProblemEntityPK implements Serializable {
    private int ordersId;
    private int problemId;

    @Column(name = "Orders_id")
    @Id
    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    @Column(name = "Problem_id")
    @Id
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

        OrdersHasProblemEntityPK that = (OrdersHasProblemEntityPK) o;

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
