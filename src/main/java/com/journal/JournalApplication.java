package com.journal;// Base Package for component scan

import java.io.IOException;
import java.util.List;

import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@Slf4j
// @Configuration(bean in that class), @EnableAutoConfiguration, @ComponentScan
public class JournalApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext aonfigurableApplicationContext = SpringApplication.run(JournalApplication.class,
		        args);
		log.info(List.of(aonfigurableApplicationContext.getEnvironment().getActiveProfiles()).toString());

	}

	PoolingHttpClientConnectionManager configureConnectionManager() {
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(5);
		poolingHttpClientConnectionManager.setMaxTotal(10);
		return poolingHttpClientConnectionManager;
	}

	@Bean
	PlatformTransactionManager get(MongoDatabaseFactory dbFactory) {// help to make connection
		return new MongoTransactionManager(dbFactory);
	}

	@Bean
	@ConditionalOnProperty(value = "custom-http-pooling", havingValue = "true", matchIfMissing = false)
	RestTemplate restTemplate() {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(configureConnectionManager())
		        .setRetryStrategy(new HttpRequestRetryStrategy() {

			        @Override
			        public TimeValue getRetryInterval(HttpResponse response, int execCount, HttpContext context) {
				        return TimeValue.ofMicroseconds(1000);
			        }

			        @Override
			        public boolean retryRequest(HttpRequest request, IOException exception, int execCount,
			                HttpContext context) {
				        return execCount < 3;
			        }

			        @Override
			        public boolean retryRequest(HttpResponse response, int execCount, HttpContext context) {
				        return execCount < 3;
			        }

		        }).build();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		factory.setConnectTimeout(5000); // 5 seconds
		factory.setReadTimeout(5000); // 5 seconds

		return new RestTemplate(factory);
	}

	@Bean
	@ConditionalOnProperty(value = "custom-http-pooling", havingValue = "false", matchIfMissing = true)
	RestTemplate restTemplateWithConnectionManager() {
		CloseableHttpClient httpClient = HttpClients.custom().build();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		factory.setConnectTimeout(5000); // 5 seconds
		factory.setReadTimeout(5000); // 5 seconds
		return new RestTemplate(factory);
	}

}
