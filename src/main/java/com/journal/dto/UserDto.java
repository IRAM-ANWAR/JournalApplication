package com.journal.dto;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.journal.entity.JournalEntry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {

	private String userName;
	private String password;
	private ObjectId id;
	private String email;
	private boolean sentimentAnalysis;
	private List<JournalEntry> journalEntries = new ArrayList<>();
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	public void addJournalEntry(JournalEntry journalEntry) {
		this.journalEntries.add(journalEntry);
	}
}
