package ru.itmo.students.springRest.config;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itmo.students.springRest.exception.ApiError;
import ru.itmo.students.springRest.exception.RadiusValueInvalidException;
import ru.itmo.students.springRest.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
@Log
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {


    @Autowired
    private MessageSource messageSource;

    private String getLocaleMessage(String msg, Locale locale) {
        return messageSource.getMessage(msg,null, locale);
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        String msg = messageSource.getMessage("args.not.valid.msg", null, request.getLocale());
        logger.error(ex.getLocalizedMessage());

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, msg, errors, request.getDescription(false).substring(4));
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }

    // fixme: 400, locales!!!!!!!!!


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = messageSource.getMessage("req.param.miss.error", new String[]{ex.getParameterName()}, request.getLocale());
        String msg = messageSource.getMessage("req.param.miss.msg", null, request.getLocale());
        logger.error(ex.getLocalizedMessage());
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, msg, error, request.getDescription(false).substring(4));
        return new ResponseEntity<Object>(
                apiError, headers, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String msg = messageSource.getMessage("req.method.not.support.msg", null, request.getLocale());
        StringBuilder errorSb = new StringBuilder();
        String error = messageSource.getMessage("req.method.not.support.error", new String[]{ex.getMethod()}, request.getLocale());
        errorSb.append(error).append(" ");
        ex.getSupportedHttpMethods().forEach(t -> errorSb.append(t).append(" "));
        logger.error(ex.getLocalizedMessage());
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, msg, errorSb.toString(), request.getDescription(false).substring(4));
        return new ResponseEntity<Object>(
                apiError, headers, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = messageSource.getMessage("media.type.not.support", new String[]{String.valueOf(ex.getContentType()), MediaType.APPLICATION_JSON_VALUE}, request.getLocale());
        ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, error, request.getDescription(false).substring(4));
        logger.error(ex.getLocalizedMessage());
        return new ResponseEntity<Object>(apiError, headers, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String msg = messageSource.getMessage("msg.not.readable", null, request.getLocale());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, msg, request.getDescription(false).substring(4));
        logger.error(ex.getLocalizedMessage());
        return new ResponseEntity<Object>(apiError, headers, apiError.getStatus());
    }


    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        String msg = messageSource.getMessage("not.found.msg", null, request.getLocale());
        logger.error(ex.getLocalizedMessage());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, msg, request.getDescription(false).substring(4));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(value = {RadiusValueInvalidException.class})
    protected ResponseEntity<Object> handleInvalidRadiusException(RuntimeException ex, WebRequest request) {
        String msg = messageSource.getMessage("invalid.radius.msg", null, request.getLocale());
        logger.error(ex.getLocalizedMessage());
        Double reqValue = ((RadiusValueInvalidException) ex).getRequestValue();
        String error = messageSource.getMessage("invalid.radius.error", new Double[]{reqValue}, request.getLocale());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, msg, error, request.getDescription(false).substring(4));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String msg = messageSource.getMessage("not.found.msg", null, request.getLocale());
        String error = messageSource.getMessage("no.handler.found.error", new String[]{ex.getRequestURL()}, request.getLocale());
        logger.error(ex.getLocalizedMessage());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, msg, error, request.getDescription(false).substring(4));
        return new ResponseEntity<Object>(apiError, headers, apiError.getStatus());
    }


}
