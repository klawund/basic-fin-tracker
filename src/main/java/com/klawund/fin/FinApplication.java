package com.klawund.fin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.klawund.fin", "com.klawund.framework"})
public class FinApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(FinApplication.class, args);
	}
}
