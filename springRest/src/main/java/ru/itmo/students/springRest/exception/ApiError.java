package ru.itmo.students.springRest.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss a")
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private String message;
    private List<String> errors;
    private String path;

    public ApiError(HttpStatus status, String message, List<String> errors, String path) {
        this.timeStamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.path = path;
    }


    public ApiError(HttpStatus status, String message, String error, String path) {
        this.timeStamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
        this.path = path;
    }

    public ApiError(HttpStatus status, String message, String path) {
        this.timeStamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = null;
        this.path = path;
    }
}
