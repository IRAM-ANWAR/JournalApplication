package com.spring.boot.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.boot.entity.ConfigJournalAppEntity;

public interface ConfigRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
