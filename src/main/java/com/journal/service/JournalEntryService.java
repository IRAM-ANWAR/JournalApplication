package com.journal.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.journal.dto.JournalEntryDto;
import com.journal.entity.JournalEntry;
import com.journal.entity.User;
import com.journal.repository.JournalEntryRepository;
import com.journal.util.GenericMapperUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JournalEntryService {

	private JournalEntryRepository journalEntryRepository;

	private UserService userService;

	private final JournalEntryService self;

	JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService,
	        JournalEntryService self) {
		this.journalEntryRepository = journalEntryRepository;
		this.userService = userService;
		this.self = self;
	}

	@Transactional
	public void deleteById(String id) {
		User user = this.userService.getByUserName();
		JournalEntry journalEntry = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id))
		        .findFirst().orElse(null);
		if (journalEntry == null)
			throw new UsernameNotFoundException("Invalid Journal ID");
		user.getJournalEntries().remove(journalEntry);
		this.userService.saveUser(user);
		this.journalEntryRepository.delete(journalEntry);
	}

	public List<JournalEntryDto> getAllEntries() {
		User user = this.userService.getByUserName();
		return GenericMapperUtil.mapListToDto(user.getJournalEntries(), JournalEntryDto.class);
	}

	public JournalEntryDto getById(String id) {
		User user = this.userService.getByUserName();
		return GenericMapperUtil.mapToDto(
		        user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id)).findFirst().orElse(null),
		        JournalEntryDto.class);

	}

	@Transactional
	public JournalEntryDto saveEntryForUser(JournalEntryDto journalEntryDto) {
		try {
			User user = this.userService.getByUserName();
			if (user == null)
				throw new UsernameNotFoundException("Invalid User");
			journalEntryDto.setDate(LocalDateTime.now());
			JournalEntry journalEntry = GenericMapperUtil.mapToEntity(journalEntryDto, JournalEntry.class);
			user.addJournalEntry(journalEntry);
			JournalEntry journalEntrySaved = this.journalEntryRepository.save(journalEntry);
			this.userService.saveUser(user);
			return GenericMapperUtil.mapToDto(journalEntrySaved, JournalEntryDto.class);
		} catch (Exception e) {
			log.error("Exception in saving journalEntry", e);
			throw e;
		}
	}

	public JournalEntryDto updateEntry(String id, JournalEntryDto journalEntryDto) {
		JournalEntryDto journalEntryExisting = this.getById(id);
		if (journalEntryExisting != null) {
			journalEntryExisting.setTitle(journalEntryDto.getTitle() != null && !journalEntryDto.getTitle().isEmpty()
			        ? journalEntryDto.getTitle()
			        : journalEntryExisting.getTitle());
			journalEntryExisting
			        .setContent(journalEntryDto.getContent() != null && !journalEntryDto.getContent().isEmpty()
			                ? journalEntryDto.getContent()
			                : journalEntryExisting.getContent());
			return this.self.saveEntryForUser(journalEntryExisting);
			// When calling @Transactional methods, you should always invoke them via an
			// injected dependency instead of calling them directly via this. This is
			// because Spring manages transactions via proxy objects, and calling a
			// transactional method directly within the same class bypasses the proxy,
			// meaning the transaction won't work as expected.

		}
		return null;
	}

}
