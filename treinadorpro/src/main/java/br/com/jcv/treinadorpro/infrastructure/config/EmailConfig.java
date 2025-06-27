package br.com.jcv.treinadorpro.infrastructure.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EmailConfig {
    @Value("${email.from-mail}")
    private String fromMail;
}
