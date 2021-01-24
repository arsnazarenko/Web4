package ru.itmo.students.springRest.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RadiusValueInvalidException extends RuntimeException {

    private Double requestValue;

    public RadiusValueInvalidException(Double requestValue) {
        this.requestValue = requestValue;
    }

    public RadiusValueInvalidException(String message, Double requestValue) {
        super(message);
        this.requestValue = requestValue;
    }
}
