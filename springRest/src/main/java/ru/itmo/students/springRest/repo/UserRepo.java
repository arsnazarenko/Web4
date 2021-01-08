package ru.itmo.students.springRest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.students.springRest.domain.User;
public interface UserRepo extends JpaRepository<User, Long> {
    User findByLogin(String name);
}
