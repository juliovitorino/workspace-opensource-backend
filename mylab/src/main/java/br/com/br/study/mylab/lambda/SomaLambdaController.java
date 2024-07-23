package br.com.br.study.mylab.lambda;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Integer> executaSoma(@Param("a") int a, @Param("b") int b, @Param("c") int c, @Param("v") int v) {
        Map<Integer, Soma> mapSomaFactory = new HashMap<>();
        mapSomaFactory.put(1, (a1, b1, c1) -> a1 + b1 + c1);
        mapSomaFactory.put(2, (a1, b1, c1) -> (a1 + b1 + c1) * 2);
        mapSomaFactory.put(3, (a1, b1, c1) -> (a1 + b1 + c1) * 3);
        mapSomaFactory.put(4, (a1, b1, c1) -> (a1 + b1 + c1) * 4);
        mapSomaFactory.put(5, (a1, b1, c1) -> (a1 + b1 + c1) * 5);

        //envia a implementação anonima (lambda) da interface e os parametros pra serem usados no serviço
        return ResponseEntity.ok(somaService.adicionaMaisDez(mapSomaFactory.get(v),a,b,c));
    }
}
