package br.com.jcv.notifier;

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
		"br.com.jcv.notifier",
		"br.com.jcv.commons.library",
		"br.com.jcv.codegen.codegenerator"
})
@EnableScheduling
@EnableAsync
@EnableFeignClients
public class NotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifierApplication.class, args);
	}

}
