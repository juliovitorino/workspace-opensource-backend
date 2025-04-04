package br.com.jcv.treinadorpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {
		"br.com.jcv.treinadorpro",
		"br.com.jcv.commons.library",
		"br.com.jcv.restclient"
})
@EnableScheduling
@EnableFeignClients(basePackages = "br.com.jcv.restclient")
public class TreinadorproApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreinadorproApplication.class, args);
	}

}
