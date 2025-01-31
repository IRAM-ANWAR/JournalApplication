package com.journal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.journal.cache.AppCache;
import com.journal.entity.WeatherResponse;
import com.journal.redis.RedisService;

@Service
public class WeatherService {

	@Value("${api.weather.key}")
	private String apiKey;

	private AppCache appCacheImpl;

	private RedisService redisService;
	private RestTemplate restTemplate;

	WeatherService(RestTemplate restTemplate, RedisService redisService, AppCache appCacheImpl) {
		this.redisService = redisService;
		this.appCacheImpl = appCacheImpl;
		this.restTemplate = restTemplate;
	}

	public WeatherResponse getWeather(String city) {

		WeatherResponse weatherResponse = this.redisService.get("weather_of_" + city, WeatherResponse.class);
		if (weatherResponse != null)
			return weatherResponse;
		String finalAPI = this.appCacheImpl.getAppCache().get(AppCache.Keys.WEATHER_API.toString().toLowerCase())
		        .replace("<CITY>", city).replace("<API_KEY>", this.apiKey);
		ResponseEntity<WeatherResponse> response = this.restTemplate.exchange(finalAPI, HttpMethod.GET, null,
		        WeatherResponse.class);// deserialize"
		this.redisService.set("weather_of_" + city, response.getBody(), 300L);
		return response.getBody();
	}
}
