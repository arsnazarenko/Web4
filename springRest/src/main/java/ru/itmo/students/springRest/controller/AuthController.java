package ru.itmo.students.springRest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.itmo.students.springRest.config.jwt.JwtProvider;
import ru.itmo.students.springRest.domain.auth.*;
import ru.itmo.students.springRest.domain.User;
import ru.itmo.students.springRest.service.UserAuthService;
import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        if (userAuthService.addNewUser(user) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new RegistrationResponse(user.getLogin(), messageSource.getMessage("reg.created",null,LocaleContextHolder.getLocale())));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new RegistrationResponse(user.getLogin(), messageSource.getMessage("reg.conflict",null, LocaleContextHolder.getLocale())));
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody @Valid AuthRequest request) {
        User user = userAuthService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null, messageSource.getMessage("auth.unauthorized",null, LocaleContextHolder.getLocale())));
        }
        String token = jwtProvider.generateToken(user.getLogin());
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(token, messageSource.getMessage("auth.authorized",null, LocaleContextHolder.getLocale())));
    }


}
