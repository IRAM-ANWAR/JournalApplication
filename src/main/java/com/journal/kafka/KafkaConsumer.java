package com.journal.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

	@KafkaListener(id = "myConsumer", topics = "weekly_sentiments", groupId = "springboot-group-1", autoStartup = "false")
	public void listen(String value, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
	        @Header(KafkaHeaders.RECEIVED_KEY) String key) {
		this.logger.info("\n\n Consumed event from topic {}: key = {} value = {} \n\n", topic, key, value);
	}
}
