package br.com.br.study.mylab.lambda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SomaLambdaController {

    @Autowired private SomaService somaService;

    @GetMapping("lambda")
    public ResponseEntity<Integer> executaSoma(@Param("a") int a, @Param("b") int b, @Param("c") int c) {
        Soma somaImpl = (a1, b1, c1) -> a1 + b1 + c1;

        //envia a implementação anonima (lambda) da interface e os parametros pra serem usados no serviço
        return ResponseEntity.ok(somaService.adicionaMaisDez(somaImpl,a,b,c));
    }
}
