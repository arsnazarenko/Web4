package ru.itmo.students.springRest.domain.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class RegistrationRequest {
    @Size(min = 4, message = "{login.min}")
    @NotBlank(message = "{login.not.blank}")
    private String login;
    @Size(min = 4, message = "{password.min}")
    @NotBlank(message = "{password.not.blank}")
    private String password;
}
