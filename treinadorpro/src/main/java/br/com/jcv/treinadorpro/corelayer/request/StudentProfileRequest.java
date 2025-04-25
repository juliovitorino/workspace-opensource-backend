package br.com.jcv.treinadorpro.corelayer.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class StudentProfileRequest extends UserBaseRequest {
    private String password;
    private Integer height;
    private BigDecimal weight;
    private String weightUnit;
}
