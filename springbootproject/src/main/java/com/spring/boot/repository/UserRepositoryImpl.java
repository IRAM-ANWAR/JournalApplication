package com.spring.boot.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.spring.boot.entity.User;

public class UserRepositoryImpl {
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<User> getUsersForSA() {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").exists(true).ne(null).and("sentimentAnalysis").is(true));
		List<User> users = this.mongoTemplate.find(query, User.class);
		return users;
	}
}
