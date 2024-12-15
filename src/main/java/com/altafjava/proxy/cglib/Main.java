package com.altafjava.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;

public class Main {
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(FooService.class);
		enhancer.setCallback(new FooMethodInterceptor());

		FooService proxy = (FooService) enhancer.create();
		proxy.performTask();
		
		Class<? extends FooService> clazz = proxy.getClass();
		System.out.println("Proxy class name: " + clazz.getCanonicalName());
		System.out.println("Parent class name: " + clazz.getSuperclass().getCanonicalName());
	}
}
