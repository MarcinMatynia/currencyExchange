package it.marcinmatynia.currencyExchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class SmartValidatorConfig {
    @Bean
    SmartValidator smartValidator() {
        return new LocalValidatorFactoryBean();
    }
}
