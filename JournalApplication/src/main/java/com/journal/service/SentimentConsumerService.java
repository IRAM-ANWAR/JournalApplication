package com.journal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.journal.entity.SentimentData;

@Service
public class SentimentConsumerService {

	@Autowired
	private EmailService emailService;

	@KafkaListener(topics = "weekly_sentiments", groupId = "springboot-group-1")
	public void consume(SentimentData sentimentData) {
		sendEmail(sentimentData);
	}

	private void sendEmail(SentimentData sentimentData) {
		this.emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week",
		        sentimentData.getSentiment());
	}

}
