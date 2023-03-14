package com.klawund.fin.entry;

import com.klawund.fin.category.Category;
import com.klawund.fin.category.CategoryService;
import com.klawund.fin.entry.dto.BudgetEntryDTO;
import com.klawund.fin.entry.dto.CreateEntryDTO;
import com.klawund.fin.entry.dto.UpdateEntryDTO;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntryService
{
	private final EntryRepository repository;
	private final CategoryService categoryService;

	public Entry create(CreateEntryDTO createEntryDTO)
	{
		final Entry.EntryBuilder entryBuilder = Entry.builder()
			.title(createEntryDTO.getTitle())
			.due(createEntryDTO.getDue())
			.ammount(createEntryDTO.getAmmount());

		final Category category = categoryService.findCategoryByNameOrCreateIfAbsent(createEntryDTO.getCategoryName());
		if (category != null)
		{
			entryBuilder.category(category);
		}

		return repository.save(entryBuilder.build());
	}

	@Transactional
	public Entry update(UpdateEntryDTO updateEntryDTO)
	{
		final Entry existingEntry = repository.findById(updateEntryDTO.getId())
			.orElseThrow(() -> new EntityNotFoundException(String.format("No Entry found for id %s", updateEntryDTO.getId())));

		updateEntryAmmount(updateEntryDTO, existingEntry);
		updateEntryDueDate(updateEntryDTO, existingEntry);
		updateEntryTitle(updateEntryDTO, existingEntry);
		updateEntryCategory(updateEntryDTO, existingEntry);

		return existingEntry;
	}

	private void updateEntryCategory(UpdateEntryDTO updateEntryDTO, Entry existingEntry)
	{
		final Category newCategory = categoryService.findCategoryByNameOrCreateIfAbsent(
			updateEntryDTO.getCategoryName());

		if (!Objects.equals(newCategory, existingEntry.getCategory()))
		{
			existingEntry.setCategory(newCategory);
		}
	}

	private void updateEntryTitle(UpdateEntryDTO updateEntryDTO, Entry existingEntry)
	{
		final String newTitle = updateEntryDTO.getTitle();
		if (newTitle != null && !newTitle.isBlank() && !Objects.equals(newTitle, existingEntry.getTitle()))
		{
			existingEntry.setTitle(newTitle);
		}
	}

	private void updateEntryDueDate(UpdateEntryDTO updateEntryDTO, Entry existingEntry)
	{
		final LocalDate newDue = updateEntryDTO.getDue();
		if (newDue != null && !Objects.equals(newDue, existingEntry.getDue()))
		{
			existingEntry.setDue(newDue);
		}
	}

	private void updateEntryAmmount(UpdateEntryDTO updateEntryDTO, Entry existingEntry)
	{
		final BigDecimal newAmmount = updateEntryDTO.getAmmount();
		if (newAmmount != null && !Objects.equals(newAmmount, existingEntry.getAmmount()))
		{
			existingEntry.setAmmount(newAmmount);
		}
	}

	public void delete(Long id)
	{
		repository.delete(repository.getReferenceById(id));
	}

	public BigDecimal sumEntriesAmmountForPeriod(LocalDate start, LocalDate end)
	{
		return repository.sumEntriesAmmountForPeriod(start, end).orElse(BigDecimal.ZERO);
	}

	public BigDecimal sumEntriesAmmountForPeriodAndCategory(LocalDate start, LocalDate end, String categoryName)
	{
		if (categoryName == null || categoryName.isBlank())
		{
			return repository.sumEntriesAmmountWithoutCategoryForPeriod(start, end).orElse(BigDecimal.ZERO);
		}
		return repository.sumEntriesAmmountForPeriodAndCategory(start, end, categoryName).orElse(BigDecimal.ZERO);
	}

	public List<BudgetEntryDTO> findBudgetEntriesForPeriod(LocalDate start, LocalDate end)
	{
		List<Entry> entries = repository.findEntriesForPeriod(start, end);
		return entries.stream().map(BudgetEntryDTO::new).toList();
	}
}
