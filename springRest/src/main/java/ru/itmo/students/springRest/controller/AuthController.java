package ru.itmo.students.springRest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.itmo.students.springRest.config.jwt.JwtProvider;
import ru.itmo.students.springRest.domain.auth.*;
import ru.itmo.students.springRest.domain.User;
import ru.itmo.students.springRest.service.UserService;
import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public RegistrationResponse registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        if (userService.addNewUser(user) != null) {
            return new RegistrationResponse(user.getLogin(), RegisterStatus.SUCCESS);
        }
        return new RegistrationResponse(user.getLogin(), RegisterStatus.RESERVED);
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }


}
