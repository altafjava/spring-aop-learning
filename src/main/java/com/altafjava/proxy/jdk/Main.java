package com.altafjava.proxy.jdk;

import java.lang.reflect.Proxy;

public class Main {

	public static void main(String[] args) {
		FooInterface foo = new FooService();
		FooInvocationHandler handler = new FooInvocationHandler(foo);

		FooInterface proxy = (FooInterface) Proxy.newProxyInstance(
				FooInterface.class.getClassLoader(),
				new Class<?>[] { FooInterface.class },
				handler);
		proxy.performTask();

		Class<? extends FooInterface> clazz = proxy.getClass();
		System.out.println("Proxy class name: " + clazz.getCanonicalName());
		System.out.println("Parent class name: " + clazz.getSuperclass().getCanonicalName());
		System.out.println("Parent Parent class name: " + clazz.getSuperclass().getSuperclass().getCanonicalName());
	}
}
