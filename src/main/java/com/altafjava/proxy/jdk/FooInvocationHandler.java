package com.altafjava.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class FooInvocationHandler implements InvocationHandler {
	private final FooInterface target;

	public FooInvocationHandler(FooInterface target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("Before method: " + method.getName());
		Object result = method.invoke(target, args);
		System.out.println("After method: " + method.getName());
		return result;
	}
}
