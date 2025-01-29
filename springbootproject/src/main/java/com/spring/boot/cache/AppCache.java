package com.spring.boot.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.boot.entity.ConfigJournalAppEntity;
import com.spring.boot.repository.ConfigRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

	public enum Keys {
		weather_api;
	}

	public Map<String, String> appCache = new HashMap<>();

	@Autowired
	ConfigRepository configRepository;

	@PostConstruct
	public void init() {
		List<ConfigJournalAppEntity> configJournalAppEntity = this.configRepository.findAll();
		configJournalAppEntity.forEach(entity -> this.appCache.put(entity.getKey(), entity.getValue()));
	}

}
