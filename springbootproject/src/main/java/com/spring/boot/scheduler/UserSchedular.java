package com.spring.boot.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.spring.boot.entity.JournalEntry;
import com.spring.boot.entity.SentimentData;
import com.spring.boot.entity.User;
import com.spring.boot.enums.Sentiment;
import com.spring.boot.repository.UserRepositoryImpl;
import com.spring.boot.service.EmailService;

@Component
public class UserSchedular {

	@Autowired
	private UserRepositoryImpl userRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	private KafkaTemplate<String, SentimentData> kafkaTemplate;

	@Scheduled(cron = "0 0 9 ? * SUN")
	public void fetchUsersAndSendSAMail() {
		List<User> users = this.userRepository.getUsersForSA();
		users.stream().forEach(user -> {
			List<Sentiment> sentiments = user.getJournalEntries().stream()
			        .filter(j -> j.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
			        .map(JournalEntry::getSentiment).collect(Collectors.toList());
			Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
			for (Sentiment sentiment : sentiments)
				if (sentiment != null)
					sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
			Sentiment mostFrequentSentiment = null;
			int maxCount = 0;
			for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet())
				if (entry.getValue() > maxCount) {
					maxCount = entry.getValue();
					mostFrequentSentiment = entry.getKey();
				}
			if (mostFrequentSentiment != null) {
				SentimentData sentimentData = SentimentData.builder().email(user.getEmail())
				        .sentiment("Sentiment for last 7 days " + mostFrequentSentiment).build();
				try {
					this.kafkaTemplate.send("weekly_sentiments", sentimentData.getEmail(), sentimentData);
				} catch (Exception e) {
					this.emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week",
					        sentimentData.getSentiment());
				}
			}

		});
	}

}
