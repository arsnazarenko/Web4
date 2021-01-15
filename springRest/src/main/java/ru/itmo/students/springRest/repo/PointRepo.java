package ru.itmo.students.springRest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.students.springRest.domain.Point;
import ru.itmo.students.springRest.domain.User;

import java.util.List;
import java.util.Optional;

public interface PointRepo extends JpaRepository<Point, Long> {
    List<Point> findByUser(User user);
    Optional<Point> findByIdAndUser(Long id, User user);
}
