package com.comics.servertfc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"model.dao","webservice"})
@EnableJpaRepositories("model.dao")
@EntityScan("model.entities")
public class ServerTfcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerTfcApplication.class, args);
	}

}
