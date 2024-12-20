package com.altafjava.examples.audit;

import java.util.concurrent.TimeUnit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Test {

	public static void main(String[] args) {
		SpringApplication.run(Test.class, args);
	}

	@Bean
	public CommandLineRunner demo(ProductRepository repository) {
		return args -> {
			System.out.println();
			// Create a new product
			AuditContext.setCurrentUser("Captain");
			Product product = new Product();
			product.setName("Laptop");
			product.setPrice(1000);
			repository.save(product);
			repository.findAll().forEach(System.out::println);

			// Update the product
			TimeUnit.SECONDS.sleep(2);
			AuditContext.setCurrentUser("Thor");
			product.setName("Gaming Laptop");
			product.setPrice(2000);
			repository.save(product);
			repository.findAll().forEach(System.out::println);

			// Update the product again
			TimeUnit.SECONDS.sleep(2);
			AuditContext.setCurrentUser("Ironman");
			product.setName("NanoTech Laptop");
			product.setPrice(9000);
			repository.save(product);
			repository.findAll().forEach(System.out::println);
			
			System.out.println();
			AuditContext.clear();
		};
	}
}
