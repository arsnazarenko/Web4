package ru.itmo.students.springRest.domain.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AuthRequest {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
