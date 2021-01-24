package ru.itmo.students.springRest.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import ru.itmo.students.springRest.exception.ApiError;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MessageSource messageSource;


    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        String msg = messageSource.getMessage("forbidden.msg", null, req.getLocale());
        String error = messageSource.getMessage("forbidden.error", new String[]{req.getRequestURI()}, req.getLocale());
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, msg, error, req.getRequestURI());
        res.getWriter().write(mapper.writeValueAsString(apiError));
    }
}
