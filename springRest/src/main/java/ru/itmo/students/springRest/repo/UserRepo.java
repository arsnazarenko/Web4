package ru.itmo.students.springRest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.students.springRest.domain.User;
public interface UserRepo extends JpaRepository<User, Long> {
    User findByLogin(String name);
}
