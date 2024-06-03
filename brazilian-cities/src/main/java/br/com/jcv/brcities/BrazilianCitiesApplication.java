package br.com.jcv.brcities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {
		"br.com.jcv.brcities",
		"br.com.jcv.commons.library",
		"br.com.jcv.restclient",
		"br.com.jcv.codegen.codegenerator"
})
@EnableScheduling
public class BrazilianCitiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrazilianCitiesApplication.class, args);
	}

}
