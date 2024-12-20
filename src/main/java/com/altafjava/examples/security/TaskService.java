package com.altafjava.examples.security;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

	@PreAuthorize("ROLE_USER")
	public void performUserTask() {
		System.out.println("Performing user-specific task");
	}

	@PreAuthorize("ROLE_ADMIN")
	@PostAuthorize("true")
//	@PostAuthorize("false")
	public String performAdminTask() {
		System.out.println("Performing admin-specific task");
		return "Admin task result";
	}
}