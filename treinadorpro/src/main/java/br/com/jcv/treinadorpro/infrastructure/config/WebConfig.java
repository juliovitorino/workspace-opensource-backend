package br.com.jcv.treinadorpro.infrastructure.config;

import br.com.jcv.treinadorpro.infrastructure.interceptor.ApiKeyInterceptor;
import br.com.jcv.treinadorpro.infrastructure.interceptor.TokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    private final ApiKeyInterceptor apiKeyInterceptor;
    private final TokenInterceptor tokenInterceptor;

    public WebConfig(ApiKeyInterceptor apiKeyInterceptor, TokenInterceptor tokenInterceptor) {
        this.apiKeyInterceptor = apiKeyInterceptor;
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor)
            .addPathPatterns("/v1/api/business/**");
        log.info("ApiKeyInterceptor has been configured successfully");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/v1/api/business/**")
                .excludePathPatterns("/v1/api/business/public/**");
        log.info("TokenInterceptor has been configured successfully");

    }
}
