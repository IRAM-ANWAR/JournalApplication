package com.spring.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.boot.cache.AppCache;
import com.spring.boot.entity.WeatherResponse;

@Service
public class WeatherService {

	private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

	@Value("${api.weather.key}")
	private String apiKey;

	@Autowired
	AppCache appCacheImpl;

	@Autowired
	RedisService redisService;

	@Autowired
	private RestTemplate restTemplate;

	public WeatherResponse getWeather(String city) {

		WeatherResponse weatherResponse = this.redisService.get("weather_of_" + city, WeatherResponse.class);
		if (weatherResponse != null)
			return weatherResponse;
		String finalAPI = this.appCacheImpl.appCache.get(AppCache.Keys.weather_api.toString()).replace("<CITY>", city)
		        .replace("<API_KEY>", this.apiKey);
		ResponseEntity<WeatherResponse> response = this.restTemplate.exchange(finalAPI, HttpMethod.GET, null,
		        WeatherResponse.class);// deserialize"
		this.redisService.set("weather_of_" + city, response.getBody(), 300L);
		return response.getBody();
	}
}
