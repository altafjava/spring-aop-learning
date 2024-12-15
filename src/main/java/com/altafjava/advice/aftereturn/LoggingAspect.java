package com.altafjava.advice.aftereturn;

import java.lang.reflect.Method;
import org.springframework.aop.AfterReturningAdvice;

/**
 * This is the Aspect class implementing cross-cutting concerns.
 */
public class LoggingAspect implements AfterReturningAdvice {

	/**
     * After Returning Advice: Executed after the method returns successfully.
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("After Returning Advice: " + method.getName() + ", Result: " + returnValue);
    }
}
