package com.spring.boot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.entity.JournalEntry;

@RestController // @Controller+ @ResponseBody
@RequestMapping("/_journal")
public class JournalController {

	private Map<String, JournalEntry> journalEntries = new HashMap<>();

	@PostMapping
	public void createEntry(@RequestBody JournalEntry journalEntry) {
		this.journalEntries.put(journalEntry.getId(), journalEntry);
	}

	@DeleteMapping("delete/{id}")
	public boolean deleteById(@PathVariable String id) {
		return this.journalEntries.remove(id) != null;
	}

	@GetMapping("get")
	public List<JournalEntry> getAll() {
		return new ArrayList<>(this.journalEntries.values());
	}

	@GetMapping("get/{id}")
	public JournalEntry getById(@PathVariable String id) {
		return this.journalEntries.get(id);
	}

	@PutMapping("update/{id}")
	public JournalEntry updateById(@PathVariable String id, @RequestBody JournalEntry journalEntry) {
		return this.journalEntries.put(id, journalEntry);
	}

}
