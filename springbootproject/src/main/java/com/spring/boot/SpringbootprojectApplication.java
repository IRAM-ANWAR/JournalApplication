package com.spring.boot;// Base Package for component scan

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
// @Configuration(bean in that class), @EnableAutoConfiguration, @ComponentScan
public class SpringbootprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootprojectApplication.class, args);
	}

	@Bean
	PlatformTransactionManager get(MongoDatabaseFactory dbFactory) {// help to make connection
		return new MongoTransactionManager(dbFactory);
	}

}
