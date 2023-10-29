package br.com.jcv.security.guardian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {
		"br.com.jcv.security.guardian",
		"br.com.jcv.commons.library",
		"br.com.jcv.codegen.codegenerator"

})
public class GuardianApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuardianApplication.class, args);
	}

}
