package ru.itmo.students.springRest.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class AuthRequest {
    @NotEmpty(message = "{login.not.empty}")
    private String login;
    @NotEmpty(message = "{password.not.empty}")
    private String password;
}
