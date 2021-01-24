package ru.itmo.students.springRest.config;

import lombok.extern.java.Log;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {

    private final List<Locale> LOCALES = Arrays.asList(new Locale("en"), new Locale("es"), new Locale("ru"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (!StringUtils.hasLength(request.getHeader("Accept-Language"))) {
            return Locale.getDefault();
        }
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
        return Locale.lookup(list, LOCALES);
    }
}
