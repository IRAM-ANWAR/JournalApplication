package com.journal.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.journal.entity.User;

public class UserRepositoryImpl {

	private MongoTemplate mongoTemplate;

	UserRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<User> getUsersForSA() {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").exists(true).ne(null).and("sentimentAnalysis").is(true));
		return this.mongoTemplate.find(query, User.class);
	}
}
