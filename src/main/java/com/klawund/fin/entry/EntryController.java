package com.klawund.fin.entry;

import com.klawund.fin.entry.dto.CreateEntryDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("entries")
@RequiredArgsConstructor
public class EntryController
{
	private final EntryService service;
	private final EntryRepository repository;

	@GetMapping
	public ResponseEntity<List<Entry>> getAll()
	{
		final List<Entry> allEntries = repository.findAll();
		if (allEntries.isEmpty())
		{
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(allEntries);
	}

	@PostMapping
	public ResponseEntity<Entry> create(@RequestBody CreateEntryDTO createEntryDTO)
	{
		return ResponseEntity.ok(service.create(createEntryDTO));
	}
	
	@PutMapping
	public ResponseEntity<Entry> update(@RequestBody Entry newEntity)
	{
		return ResponseEntity.ok(service.update(newEntity));
	}

	@DeleteMapping
	@RequestMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id)
	{
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
