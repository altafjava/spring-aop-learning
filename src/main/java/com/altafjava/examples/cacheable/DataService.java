package com.altafjava.examples.cacheable;

import org.springframework.stereotype.Service;

@Service
public class DataService {

	@Cacheable(cacheName = "dataCache")
	public String fetchData(String key) {
		return "Data for key: " + key;
	}

	@CacheEvict(cacheName = "dataCache", allEntries = false)
	public void evictData(String key) {
		System.out.println("Evicted data for key: " + key);
	}

	@CacheEvict(cacheName = "dataCache", allEntries = true)
	public void clearCache() {
		System.out.println("Cleared all data in cache");
	}
}
