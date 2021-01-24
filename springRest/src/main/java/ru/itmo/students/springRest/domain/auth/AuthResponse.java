package ru.itmo.students.springRest.domain.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String token;
    private String message;

}
