package ru.itmo.students.springRest.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    private String login;
    private RegisterStatus status;


}
