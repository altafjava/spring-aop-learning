package com.altafjava.examples.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SecurityContext {
	private static final ThreadLocal<Set<String>> roles = ThreadLocal.withInitial(HashSet::new);
	private static final Map<String, Set<String>> roleHierarchy = new HashMap<>();

	static {
		roleHierarchy.put("ROLE_ADMIN", Set.of("ROLE_USER")); // Admin inherits User permissions
	}

	public static void addRole(String role) {
		roles.get().add(role);
	}

	public static boolean hasRole(String role) {
		Set<String> userRoles = roles.get();
		if (userRoles.contains(role)) {
			return true;
		}
		return userRoles.stream().anyMatch(userRole -> roleHierarchy.getOrDefault(userRole, Set.of()).contains(role));
	}

	public static boolean evaluateCondition(String condition, Object result) {
		if ("true".equals(condition)) {
			return true;
		}
		if ("false".equals(condition)) {
			return false;
		}
		return true;
	}

	public static void clear() {
		roles.remove();
	}
}