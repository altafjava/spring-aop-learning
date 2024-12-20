package com.altafjava.examples.cacheable;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.altafjava.examples.cacheable")
public class Test {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Test.class);
		DataService dataService = context.getBean(DataService.class);

		System.out.println("Fetching data for key1:");
		System.out.println(dataService.fetchData("key1")); // Cache miss

		System.out.println("\nFetching data for key1 again:");
		System.out.println(dataService.fetchData("key1")); // Cache hit

		System.out.println("\nFetching data for key2:");
		System.out.println(dataService.fetchData("key2")); // Cache miss

		System.out.println("\nEvicting data for key1:");
		dataService.evictData("key1");

		System.out.println("\nFetching data for key1 after eviction:");
		System.out.println(dataService.fetchData("key1")); // Cache miss

		System.out.println("\nClearing all cache entries:");
		dataService.clearCache();

		System.out.println("\nFetching data for key2 after clearing cache:");
		System.out.println(dataService.fetchData("key2")); // Cache miss

		context.close();
	}
}
