package com.altafjava.examples.exception;

import java.lang.reflect.Method;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {

	private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

	@AfterThrowing(pointcut = "execution(* com.altafjava.examples.exception.*.*(..))", throwing = "ex")
	public void handleGlobalException(Throwable ex) {
		if (!globalExceptionHandler.getClass().isAnnotationPresent(ControllerAdvice.class)) {
			return; // Do nothing if @ControllerAdvice is not present
		}

		boolean handled = false;
		Method[] methods = globalExceptionHandler.getClass().getDeclaredMethods();
		for (Method method : methods) {
			// Check if method is annotated with @ExceptionHandler and if it handles the
			if (method.isAnnotationPresent(ExceptionHandler.class) && method.getParameterCount() == 1
					&& method.getParameterTypes()[0].isAssignableFrom(ex.getClass())) {
				try {
					method.invoke(globalExceptionHandler, ex); // Invoke the correct handler method
					handled = true; // Mark the exception as handled
					return;
				} catch (Exception e) {
					System.err.println("Error invoking global exception handler: " + e.getMessage());
				}
			}
		}

		// If no handler was found, just let the exception propagate
		if (!handled) {
			System.err.println("Exception handling not found for: " + ex.getClass().getSimpleName());
			ex.printStackTrace();
		}
	}
}
