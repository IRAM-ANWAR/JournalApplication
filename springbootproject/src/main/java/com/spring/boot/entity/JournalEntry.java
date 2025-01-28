package com.spring.boot.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Document(collection = "journal_entries")
public class JournalEntry {
	@Id
	private String id;
	@NonNull
	private String title;
	private String content;
	private LocalDateTime date;
}
