package backend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

//@Entity
//@Table(name = "Orders_has_Problem", schema = "mydb")
public class OrdersHasProblem implements Serializable {

    @ManyToOne(optional = false)
    private Orders orders;

    @ManyToOne(optional = false)
    private Problem problem;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersHasProblem that = (OrdersHasProblem) o;
        return Objects.equals(orders, that.orders) && Objects.equals(problem, that.problem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders, problem);
    }

    @Override
    public String toString() {
        return "OrdersHasProblem{" +
                "orders=" + orders +
                ", problem=" + problem +
                '}';
    }
}
