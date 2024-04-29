package br.com.jcv.preferences;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {
		"br.com.jcv.preferences",
		"br.com.jcv.commons.library",
		"br.com.jcv.codegen.codegenerator"
})
@EnableScheduling
@EnableAsync
public class PreferencesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreferencesApplication.class, args);
	}

}
