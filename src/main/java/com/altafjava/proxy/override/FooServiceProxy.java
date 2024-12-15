package com.altafjava.proxy.override;

import com.altafjava.proxy.cglib.FooService;

public class FooServiceProxy extends FooService {
	@Override
	public void performTask() {
		System.out.println("Before method: performTask");
		super.performTask();
		System.out.println("After method: performTask");
	}
}
