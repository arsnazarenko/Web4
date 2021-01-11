package ru.itmo.students.springRest.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;

}
