package com.journal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.journal.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {

}
