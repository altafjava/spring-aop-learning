package com.altafjava.examples.cacheable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * Simulates a cache manager for storing and retrieving cache entries.
 */
@Component
public class CacheManager {
	private final Map<String, Map<Object, Object>> caches = new ConcurrentHashMap<>();

	public Map<Object, Object> getCache(String cacheName) {
		return caches.computeIfAbsent(cacheName, k -> new ConcurrentHashMap<>());
	}

	public void clearCache(String cacheName) {
		Map<Object, Object> cache = caches.get(cacheName);
		if (cache != null) {
			cache.clear();
		}
	}

	public void removeCacheEntry(String cacheName, Object key) {
		Map<Object, Object> cache = caches.get(cacheName);
		if (cache != null) {
			cache.remove(key);
		}
	}
}
