package backend.repository;

import backend.entity.TimeRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRangeRepository extends JpaRepository<TimeRange, Integer> {
}
