package ru.itmo.students.springRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;


public class CustomErrorController extends BasicErrorController {
    public CustomErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = this.getStatus(request);
        if (HttpStatus.NOT_FOUND.value() == status.value()) {
            Map<String, Object> model = Collections.unmodifiableMap(this.getErrorAttributes(request, this.getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
            response.setStatus(status.value());
            ModelAndView modelAndView = this.resolveErrorView(request, response, status, model);
            return modelAndView != null ? modelAndView : new ModelAndView("/", model);
        }
        return super.errorHtml(request, response);
    }


}
