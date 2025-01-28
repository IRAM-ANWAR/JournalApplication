package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.entity.JournalEntry;
import com.spring.boot.entity.User;
import com.spring.boot.repository.JournalEntryRepository;
import com.spring.boot.repository.UserRepository;

@Component
public class UserService {

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JournalEntryRepository journalEntryRepository;

	@Transactional
	public boolean deleteByUserName() {
		User user = getByUserName();
		List<JournalEntry> journalEntries = user.getJournalEntries();
		this.journalEntryRepository.deleteAll(journalEntries);
		return this.userRepository.deleteByUserName(user.getUserName()) == null == false;
	}

	public List<User> getAllUsers() {
		// Authentication authentication =
		// SecurityContextHolder.getContext().getAuthentication();
		// String userName = authentication.getName();
		return this.userRepository.findAll();
	}

	public User getByUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		return this.userRepository.findByUserName(userName).orElse(null);
	}

	public User saveAdminUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(List.of("ADMIN"));
		return this.userRepository.save(user);
	}

	public User saveNewUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(List.of("USER"));
		return this.userRepository.save(user);
	}

	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

	public User updateEntry(User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User userExisting = this.userRepository.findByUserName(userName).orElse(null);
		if (userExisting != null) {
			userExisting.setPassword(user.getPassword() != null ? passwordEncoder.encode(user.getPassword())
					: userExisting.getPassword());
			userExisting.setJournalEntries(
					user.getJournalEntries() != null ? user.getJournalEntries() : userExisting.getJournalEntries());
			return saveUser(userExisting);
		}
		return null;
	}

}
