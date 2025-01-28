package com.spring.boot.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.entity.JournalEntry;
import com.spring.boot.entity.User;
import com.spring.boot.repository.JournalEntryRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository journalEntryRepository;

	@Autowired
	UserService userService;

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

	public List<JournalEntry> getAllEntries() {
		User user = this.userService.getByUserName();
		return user.getJournalEntries();
	}

	public JournalEntry getById(String id) {
		// TODO Auto-generated method stub
		User user = this.userService.getByUserName();
		return user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id)).findFirst().orElse(null);
	}

	@Transactional
	public JournalEntry saveEntryForUser(JournalEntry journalEntry) {
		try {
			User user = this.userService.getByUserName();
			if (user == null)
				throw new UsernameNotFoundException("Invalid User");
			journalEntry.setDate(LocalDateTime.now());
			user.addJournalEntry(journalEntry);
			JournalEntry journalEntrySaved = this.journalEntryRepository.save(journalEntry);
			this.userService.saveUser(user);
			return journalEntrySaved;
		} catch (Exception e) {
			log.error("Exception in saving journalEntry", e);
			throw e;
		}
	}

	public JournalEntry updateEntry(String id, JournalEntry journalEntry) {
		JournalEntry journalEntryExisting = this.getById(id);
		if (journalEntryExisting != null) {
			journalEntryExisting.setTitle(
					journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle()
							: journalEntryExisting.getTitle());
			journalEntryExisting.setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty()
					? journalEntry.getContent()
					: journalEntryExisting.getContent());
			return saveEntryForUser(journalEntryExisting);
		}
		return null;
	}

}
