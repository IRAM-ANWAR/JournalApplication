package com.spring.boot.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
	@NonNull
	@Indexed(unique = true)
	private String userName;
	@NonNull
	private String password;
	@Id
	private ObjectId id;
	@DBRef
	private List<JournalEntry> journalEntries = new ArrayList<>();
	private List<String> roles;

	public void addJournalEntry(JournalEntry journalEntry) {
		this.journalEntries.add(journalEntry);
		// TODO Auto-generated method stub
	}
}