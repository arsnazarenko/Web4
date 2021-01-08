package ru.itmo.students.springRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.students.springRest.domain.User;
import ru.itmo.students.springRest.domain.UserRole;
import ru.itmo.students.springRest.repo.UserRepo;

@Service
public class UsersService {
    @Autowired
    private  UserRepo userRepo;

    @Autowired
    private  PasswordEncoder passwordEncoder;


    public User saveUser(User user) {
        user.setRole(UserRole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //если пользователя с таким логином нет
        if (findByLogin(user.getLogin()) == null) {
            return userRepo.save(user);
        }
        return null;
    }

    public User findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (user != null) {
            if(passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
