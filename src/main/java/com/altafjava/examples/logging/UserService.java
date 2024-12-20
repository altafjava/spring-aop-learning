package com.altafjava.examples.logging;

import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@LogExecution
	@MonitorPerformance
	public void createUser(String username, String email) throws InterruptedException {
		System.out.println("UserService, Creating user: " + username + " with email: " + email);
		TimeUnit.MILLISECONDS.sleep(100); // Simulate some processing time
	}

	@LogExecution
	@MonitorPerformance
	public void updateUser(Long id, String username, String email) throws InterruptedException {
		System.out.println("UserService, Updating user with username: " + username + " and email: " + email);
		TimeUnit.MILLISECONDS.sleep(150); // Simulate some processing time
	}

	@LogExecution
	@MonitorPerformance
	public void deleteUser(Long id) throws InterruptedException {
		System.out.println("UserService, Deleting user with ID: " + id);
		TimeUnit.MILLISECONDS.sleep(50); // Simulate some processing time
	}
}