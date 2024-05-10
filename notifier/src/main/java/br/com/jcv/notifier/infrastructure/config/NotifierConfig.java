package br.com.jcv.notifier.infrastructure.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class NotifierConfig {
    @Value("${spring.datasource.driverClassName}")
    private String dbDriverClassName;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.url}")
    private String dbUrl;

}
