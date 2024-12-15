package com.altafjava.proxy.override;

import com.altafjava.proxy.cglib.FooService;

public class Main {

	public static void main(String[] args) {
		FooService fooService = new FooServiceProxy();
		fooService.performTask();
	}
}
