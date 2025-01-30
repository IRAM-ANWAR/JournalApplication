package com.journal.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.journal.entity.ConfigJournalAppEntity;

public interface ConfigRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
