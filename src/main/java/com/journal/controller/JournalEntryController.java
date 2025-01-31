package com.journal.controller;

import java.util.List;

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

import com.journal.dto.JournalEntryDto;
import com.journal.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

	private JournalEntryService journalEntryService;

	JournalEntryController(JournalEntryService journalEntryService) {
		this.journalEntryService = journalEntryService;
	}

	@PostMapping
	public ResponseEntity<JournalEntryDto> createEntryForUser(@RequestBody JournalEntryDto journalEntryDto) {
		JournalEntryDto journalEntrySaved = this.journalEntryService.saveEntryForUser(journalEntryDto);
		HttpStatus httpStatus = null;
		httpStatus = journalEntrySaved != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(journalEntrySaved, httpStatus);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable String id) {
		this.journalEntryService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/get")
	public ResponseEntity<List<JournalEntryDto>> getAllJournalEntriesOfUser() {
		List<JournalEntryDto> journalEntryList = this.journalEntryService.getAllEntries();
		HttpStatus httpStatus = null;
		httpStatus = journalEntryList != null && !journalEntryList.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(journalEntryList, httpStatus);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<JournalEntryDto> getJournalEntryOfUser(@PathVariable String id) {
		JournalEntryDto journalEntry = this.journalEntryService.getById(id);
		HttpStatus httpStatus = null;
		httpStatus = journalEntry != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(journalEntry, httpStatus);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<JournalEntryDto> updateById(@PathVariable String id,
	        @RequestBody JournalEntryDto journalEntryDto) {
		HttpStatus httpStatus = null;
		JournalEntryDto journalEntryUpdated = this.journalEntryService.updateEntry(id, journalEntryDto);
		httpStatus = journalEntryUpdated != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(journalEntryUpdated, httpStatus);
	}

}
