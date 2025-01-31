package com.journal.dto;

import java.time.LocalDateTime;

import com.journal.enums.Sentiment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalEntryDto {

	private String id;
	private String title;
	private String content;
	private LocalDateTime date;
	private Sentiment sentiment;

}
