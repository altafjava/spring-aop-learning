package com.altafjava.examples.audit;

public class AuditContext {
	private static final ThreadLocal<String> currentUser = ThreadLocal.withInitial(() -> "system");

	public static void setCurrentUser(String user) {
		currentUser.set(user);
	}

	public static String getCurrentUser() {
		return currentUser.get();
	}

	public static void clear() {
		currentUser.remove();
	}
}
