package ru.itmo.students.springRest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.students.springRest.config.jwt.JwtProvider;
import ru.itmo.students.springRest.controller.auth.*;
import ru.itmo.students.springRest.domain.User;
import ru.itmo.students.springRest.service.UsersService;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private UsersService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public RegistrationResponse registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        if (userService.saveUser(user) != null) {
            return new RegistrationResponse(user.getLogin(), RegisterStatus.SUCCESS);
        }
        return new RegistrationResponse(user.getLogin(), RegisterStatus.RESERVED);
        //FIXME: исправить генерацию одной последовательности числовой на все таблицы!
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        System.out.println(user);
        String token = jwtProvider.generateToken(user.getLogin());
        System.out.println(token);
        return new AuthResponse(token);
    }


}
