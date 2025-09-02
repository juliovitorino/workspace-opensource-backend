package br.com.jcv.treinadorpro.corelayer.dto;

import br.com.jcv.treinadorpro.corelayer.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentFeatureDTO {

    private Long id;
    private UserDTO studentUser;
    private String password;
    private Integer height;
    private BigDecimal weight;
    private String weightUnit;
    private StatusEnum status = StatusEnum.A;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
