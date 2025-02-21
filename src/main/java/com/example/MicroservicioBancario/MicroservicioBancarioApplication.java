package com.example.MicroservicioBancario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MicroservicioBancarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioBancarioApplication.class, args);
	}

}
