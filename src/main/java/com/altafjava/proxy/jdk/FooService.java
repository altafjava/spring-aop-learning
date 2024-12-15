package com.altafjava.proxy.jdk;

public class FooService implements FooInterface {
	@Override
	public void performTask() {
		System.out.println("Performing task in FooService");
	}
}
