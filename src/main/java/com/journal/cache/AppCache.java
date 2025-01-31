package com.journal.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.journal.entity.ConfigJournalAppEntity;
import com.journal.repository.ConfigRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

	public enum Keys {
		WEATHER_API;
	}

	private Map<String, String> appCache = new HashMap<>();

	ConfigRepository configRepository;

	AppCache(ConfigRepository configRepository) {
		this.configRepository = configRepository;
	}

	public Map<String, String> getAppCache() {
		return this.appCache;
	}

	@PostConstruct
	public void init() {
		List<ConfigJournalAppEntity> configJournalAppEntity = this.configRepository.findAll();
		configJournalAppEntity.forEach(entity -> this.appCache.put(entity.getKey(), entity.getValue()));
	}

	public void setAppCache(Map<String, String> appCache) {
		this.appCache = appCache;
	}

}
