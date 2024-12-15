package com.altafjava.proxy.cglib;

import java.lang.reflect.Method;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class FooMethodInterceptor implements MethodInterceptor {
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("Before method: " + method.getName());
		Object result = proxy.invokeSuper(obj, args);
		System.out.println("After method: " + method.getName());
		return result;
	}
}
