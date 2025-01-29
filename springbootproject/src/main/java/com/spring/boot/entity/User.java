package com.spring.boot.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Document(collection = "users")
@Builder
@AllArgsConstructor
public class User {
	@NonNull
	@Indexed(unique = true)
	private String userName;
	@NonNull
	private String password;
	@Id
	private ObjectId id;
	private String email;
	private boolean sentimentAnalysis;
	@DBRef
	private List<JournalEntry> journalEntries = new ArrayList<>();
	@Builder.Default
	private List<String> roles = new ArrayList<>();;

	public void addJournalEntry(JournalEntry journalEntry) {
		this.journalEntries.add(journalEntry);
		// TODO Auto-generated method stub
	}
}