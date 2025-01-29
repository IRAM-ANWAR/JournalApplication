package com.spring.boot.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Document(collection = "config_journal_app")
@Builder
@AllArgsConstructor
public class ConfigJournalAppEntity {
	@NonNull
	private String key;
	@NonNull
	private String value;
	@Id
	private ObjectId id;
}