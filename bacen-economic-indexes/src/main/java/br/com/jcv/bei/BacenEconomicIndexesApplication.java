package br.com.jcv.bei;

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
		"br.com.jcv.bei",
		"br.com.jcv.commons.library",
		"br.com.jcv.codegen.codegenerator"
})
@EnableScheduling
//@EnableAsync
@EnableFeignClients
public class BacenEconomicIndexesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BacenEconomicIndexesApplication.class, args);
	}

}
