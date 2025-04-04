package br.com.br.study.mylab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MylabApplication {

	public static void main(String[] args) {
		SpringApplication.run(MylabApplication.class, args);
	}

}
