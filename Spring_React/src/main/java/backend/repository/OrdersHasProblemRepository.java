package backend.repository;

import backend.entity.Address;
import backend.entity.OrdersHasProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersHasProblemRepository extends JpaRepository<OrdersHasProblem, Integer> {
}
