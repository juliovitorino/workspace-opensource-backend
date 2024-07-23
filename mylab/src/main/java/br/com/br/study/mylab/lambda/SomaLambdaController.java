package br.com.br.study.mylab.lambda;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SomaLambdaController {

    @GetMapping("lambda")
    public ResponseEntity<Integer> executaSoma(@Param("a") int a, @Param("b") int b, @Param("c") int c) {
        Soma resultado = (a1, b1, c1) -> a1 + b1 + c1;
        return ResponseEntity.ok(resultado.execute(a, b, c));
    }
}
