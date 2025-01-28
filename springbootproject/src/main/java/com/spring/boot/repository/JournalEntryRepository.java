package com.spring.boot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.boot.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {

}
