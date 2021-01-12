package ru.itmo.students.springRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itmo.students.springRest.config.jwt.CustomUserDetails;
import ru.itmo.students.springRest.domain.User;
@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersService usersService;


    @Override
    public CustomUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = usersService.findByLogin(s);
        if (user == null) {
            throw new UsernameNotFoundException("User with login " + s + " not found");
        }
        return CustomUserDetails.fromUserToCustomUserDetails(user);
    }
}
