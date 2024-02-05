package dev.akorovai.AdvancedToDoAPI;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdvancedToDoApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdvancedToDoApiApplication.class, args);
	}
	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}
}
