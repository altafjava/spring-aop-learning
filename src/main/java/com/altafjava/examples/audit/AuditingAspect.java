package com.altafjava.examples.audit;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditingAspect {

	@Before("execution(* org.springframework.data.repository.CrudRepository+.save(..)) && args(entity)")
	public void auditEntity(Object entity) throws IllegalAccessException {
		Class<?> clazz = entity.getClass();
		if (clazz.isAnnotationPresent(Auditable.class)) {
			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);

				if (field.isAnnotationPresent(CreatedBy.class)) {
					if (field.get(entity) == null) {
						field.set(entity, AuditContext.getCurrentUser());
					}
				} else if (field.isAnnotationPresent(LastModifiedBy.class)) {
					field.set(entity, AuditContext.getCurrentUser());
				} else if (field.isAnnotationPresent(CreatedDate.class)) {
					if (field.get(entity) == null) {
						field.set(entity, LocalDateTime.now());
					}
				} else if (field.isAnnotationPresent(LastModifiedDate.class)) {
					field.set(entity, LocalDateTime.now());
				} else if (field.isAnnotationPresent(Version.class)) {
					Integer currentVersion = (Integer) field.get(entity);
					field.set(entity, currentVersion == null ? 0 : currentVersion + 1);
				}
			}
		}
	}
}
