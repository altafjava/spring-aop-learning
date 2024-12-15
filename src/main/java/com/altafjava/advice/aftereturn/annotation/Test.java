package com.altafjava.advice.aftereturn.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.altafjava.advice.Calculator;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.altafjava.advice.aftereturn")
public class Test {

	@Bean
    public Calculator calculator() {
        return new Calculator();
    }
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Test.class);
		Calculator calculator = context.getBean(Calculator.class); // Proxy object

		calculator.add(10, 20); // Pointcut: add()
		calculator.subtract(30, 15); // Pointcut: subtract()
		calculator.multiply(5, 4); // Pointcut: multiply()
		try {
			calculator.divide(10, 0); // Pointcut: divide()
		} catch (Exception ignored) {
		}
		context.close();
	}
}
