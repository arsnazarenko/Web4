package ru.itmo.students.springRest.config;
import org.omg.IOP.Encoding;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import ru.itmo.students.springRest.config.CustomLocaleResolver;
import ru.itmo.students.springRest.controller.CustomErrorController;

import javax.sound.sampled.AudioFormat;
import java.security.spec.EncodedKeySpec;
import java.util.Locale;

@Configuration
public class LocaleConfig {




    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("ISO-8859-1");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CustomLocaleResolver resolver = new CustomLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        Locale.setDefault(Locale.ENGLISH);
        return resolver;
    }


    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }


    @Bean
    public BasicErrorController basicErrorController() {
        return new CustomErrorController(new DefaultErrorAttributes(), new ErrorProperties());
    }

}
