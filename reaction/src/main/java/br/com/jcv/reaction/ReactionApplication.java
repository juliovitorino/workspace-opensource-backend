package br.com.jcv.reaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {
		"br.com.jcv.reaction",
		"br.com.jcv.commons.library",
		"br.com.jcv.restclient",
		"br.com.jcv.codegen.codegenerator"
})
@EnableScheduling
@EnableFeignClients
public class ReactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactionApplication.class, args);
	}

}
