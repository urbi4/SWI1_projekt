package backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OrderHasProblem", schema = "mydb")
public class OrdersHasProblem {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "problem_id", referencedColumnName = "id")
    @ManyToOne
    private Problem problem;

    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    @ManyToOne
    private Orders orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersHasProblem that = (OrdersHasProblem) o;
        return id == that.id && Objects.equals(problem, that.problem) && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, problem, orders);
    }

    @Override
    public String toString() {
        return "OrdersHasProblem{" +
                "id=" + id +
                ", problem=" + problem +
                ", orders=" + orders +
                '}';
    }
}
