package com.altafjava.advice.afterthrow;

import org.springframework.aop.framework.ProxyFactory;
import com.altafjava.advice.Calculator;

public class Test {

	/**
	 * The process of advising the Aspect on the Target class based on the Pointcut to get the Proxy object is called as
	 * Weaving.
	 */
	public static void main(String[] args) {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(new Calculator());
		proxyFactory.addAdvice(new LoggingAspect()); // Adding Aspect (cross-cutting concern)
		// Programmatic approach by default advice the aspect to all methods of the target class.

		Calculator proxyCalculator = (Calculator) proxyFactory.getProxy(); // Proxy object
		proxyCalculator.add(10, 20); // Pointcut: add()
		proxyCalculator.subtract(30, 15); // Pointcut: subtract()
		proxyCalculator.multiply(5, 4); // Pointcut: multiply()
		try {
			proxyCalculator.divide(10, 0); // Pointcut: divide()
		} catch (Exception ignored) {
		}
	}
}
