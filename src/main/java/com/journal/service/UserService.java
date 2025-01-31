package com.journal.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.journal.dto.UserDto;
import com.journal.entity.JournalEntry;
import com.journal.entity.User;
import com.journal.repository.JournalEntryRepository;
import com.journal.repository.UserRepository;
import com.journal.util.GenericMapperUtil;

@Component
public class UserService {

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private UserRepository userRepository;

	private JournalEntryRepository journalEntryRepository;

	UserService(UserRepository userRepository, JournalEntryRepository journalEntryRepository) {
		this.journalEntryRepository = journalEntryRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public boolean deleteByUserName() {
		User user = getByUserName();
		List<JournalEntry> journalEntries = user.getJournalEntries();
		this.journalEntryRepository.deleteAll(journalEntries);
		return this.userRepository.deleteByUserName(user.getUserName()) != null;
	}

	public List<UserDto> getAllUsers() {
		return GenericMapperUtil.mapListToDto(this.userRepository.findAll(), UserDto.class);
	}

	public User getByUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		return this.userRepository.findByUserName(userName).orElse(null);
	}

	public UserDto saveAdminUser(UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userDto.setRoles(List.of("ADMIN"));
		return GenericMapperUtil.mapToDto(this.userRepository.save(GenericMapperUtil.mapToEntity(userDto, User.class)),
		        UserDto.class);
	}

	public UserDto saveNewUser(UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userDto.setRoles(List.of("USER"));
		return GenericMapperUtil.mapToDto(this.userRepository.save(GenericMapperUtil.mapToEntity(userDto, User.class)),
		        UserDto.class);
	}

	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

	public UserDto updateEntry(UserDto userDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User userExisting = this.userRepository.findByUserName(userName).orElse(null);
		if (userExisting != null) {
			userExisting.setPassword(userDto.getPassword() != null ? passwordEncoder.encode(userDto.getPassword())
			        : userExisting.getPassword());
			userExisting.setJournalEntries(userDto.getJournalEntries() != null ? userDto.getJournalEntries()
			        : userExisting.getJournalEntries());
			return GenericMapperUtil.mapToDto(saveUser(userExisting), UserDto.class);
		}
		return null;
	}

}
