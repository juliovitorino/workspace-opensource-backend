package br.com.jcv.treinadorpro.infrastructure.interceptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorInterceptorResponse implements Serializable {
    private String message;
    private int code;
}
