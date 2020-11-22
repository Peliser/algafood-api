package com.algaworks.algafood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        // Especifica o messageSource que é o messages.properties (utilizado pelo Ebean)
        // para o BeanValidation quando o mesmo for resolver suas messages.
        // Se não especificar o messageSource, o BeanValidation usa o
        // ValidationMessage.properties (seu arquivo padrão).
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

}
