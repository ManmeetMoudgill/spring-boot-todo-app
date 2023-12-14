package com.manmeet.moudgill.NewTodoApplication;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class NewTodoApplication {

	private ModelMapper mMapper;

	public static void main(String[] args) {
		SpringApplication.run(NewTodoApplication.class, args);
		System.out.println("Application is running!!");
	}

	@Bean
	public ModelMapper modelMapper() {
		if (this.mMapper == null) {
			this.mMapper = new ModelMapper();
		}

		return mMapper;
	}
}
