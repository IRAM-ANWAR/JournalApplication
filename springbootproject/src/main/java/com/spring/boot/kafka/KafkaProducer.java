package com.spring.boot.kafka;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
	private static final String TOPIC = "weekly_sentiments";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String key, String value) {
		CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC, key, value);

		future.whenComplete((result, ex) -> {
			if (ex != null)
				logger.error("Failed to send message to Kafka: {}", ex.getMessage(), ex);
			else
				logger.info("Produced event to topic {}: key = {} value = {}", TOPIC, key, value);
		});
	}
}
