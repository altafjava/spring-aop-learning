package com.altafjava.examples.cacheable;

import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CacheAspect {
	private final CacheManager cacheManager;

	public CacheAspect(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Around("@annotation(cacheable)")
	public Object handleCacheable(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
		String cacheName = cacheable.cacheName();
		Map<Object, Object> cache = cacheManager.getCache(cacheName);

		// Use method arguments as the cache key
		Object key = joinPoint.getArgs()[0];

		if (cache.containsKey(key)) {
			System.out.println("Cache hit for key: " + key);
			return cache.get(key);
		}

		System.out.println("Cache miss for key: " + key);
		Object result = joinPoint.proceed();
		cache.put(key, result);
		return result;
	}

	@Around("@annotation(cacheEvict)")
	public Object handleCacheEvict(ProceedingJoinPoint joinPoint, CacheEvict cacheEvict) throws Throwable {
		String cacheName = cacheEvict.cacheName();
		if (cacheEvict.allEntries()) {
			System.out.println("Cache Evict all entries: " + cacheName);
			cacheManager.clearCache(cacheName);
		} else {
			Object key = joinPoint.getArgs()[0];
			System.out.println("Cache Evict for key: " + key);
			cacheManager.removeCacheEntry(cacheName, key);
		}
		return joinPoint.proceed();
	}
}
