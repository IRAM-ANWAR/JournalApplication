package com.spring.boot.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.spring.boot.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {

	Long deleteByUserName(String userName);

	Optional<User> findByUserName(String userName);
}
