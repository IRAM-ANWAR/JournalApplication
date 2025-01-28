package com.spring.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.entity.JournalEntry;
import com.spring.boot.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

	@Autowired
	private JournalEntryService journalEntryService;

	@PostMapping
	public ResponseEntity<?> createEntryForUser(@RequestBody JournalEntry journalEntry) {
		JournalEntry journalEntrySaved = this.journalEntryService.saveEntryForUser(journalEntry);
		HttpStatus httpStatus = null;
		httpStatus = journalEntrySaved != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(journalEntry, httpStatus);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		this.journalEntryService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/get")
	public ResponseEntity<?> getAllJournalEntriesOfUser() {
		List<JournalEntry> journalEntryList = this.journalEntryService.getAllEntries();
		HttpStatus httpStatus = null;
		httpStatus = journalEntryList != null && !journalEntryList.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(journalEntryList, httpStatus);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getJournalEntryOfUser(@PathVariable String id) {
		JournalEntry journalEntry = this.journalEntryService.getById(id);
		HttpStatus httpStatus = null;
		httpStatus = journalEntry != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(journalEntry, httpStatus);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<JournalEntry> updateById(@PathVariable String id, @RequestBody JournalEntry journalEntry) {
		HttpStatus httpStatus = null;
		JournalEntry journalEntryUpdated = this.journalEntryService.updateEntry(id, journalEntry);
		httpStatus = journalEntryUpdated != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(journalEntry, httpStatus);
	}

}
