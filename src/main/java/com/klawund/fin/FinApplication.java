package com.klawund.fin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.klawund")
public class FinApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(FinApplication.class, args);
	}
}
