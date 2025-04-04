package br.com.br.study.mylab.lambda;

import org.springframework.stereotype.Service;

@Service
public class SomaService {

    public int adicionaMaisDez(Soma soma, int a, int b, int c) {
        return 10 + soma.execute(a,b,c);
    }
}
