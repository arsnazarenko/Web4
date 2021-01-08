package ru.itmo.students.springRest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.students.springRest.domain.Point;

public interface PointRepo extends JpaRepository<Point, Long> {
}
