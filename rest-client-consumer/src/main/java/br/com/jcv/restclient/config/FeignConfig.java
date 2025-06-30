package br.com.jcv.restclient.config;

import br.com.jcv.restclient.decoder.GuardianIntegrationErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new GuardianIntegrationErrorDecoder();
    }
}
