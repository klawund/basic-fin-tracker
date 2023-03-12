package com.klawund.fin.entry;

import static java.time.temporal.TemporalAdjusters.*;

import com.klawund.fin.entry.dto.BudgetEntryDTO;
import com.klawund.fin.entry.dto.CreateEntryDTO;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntryService
{
	private final EntryRepository repository;

	public Entry create(CreateEntryDTO createEntryDTO)
	{
		final Entry entry = Entry.builder()
			.title(createEntryDTO.getTitle())
			.due(createEntryDTO.getDue())
			.ammount(createEntryDTO.getAmmount())
			.build();

		return repository.save(entry);
	}

	@Transactional
	public Entry update(Entry newEntity)
	{
		final Entry existingEntity = repository.findById(newEntity.getId())
			.orElseThrow(() -> new EntityNotFoundException(String.format("No Entry found for id %s", newEntity.getId())));

		final BigDecimal newAmmount = newEntity.getAmmount();
		if (newAmmount != null && !Objects.equals(newAmmount, existingEntity.getAmmount()))
		{
			existingEntity.setAmmount(newAmmount);
		}

		final LocalDate newDue = newEntity.getDue();
		if (newDue != null && !Objects.equals(newDue, existingEntity.getDue()))
		{
			existingEntity.setDue(newDue);
		}

		final String newTitle = newEntity.getTitle();
		if (newTitle != null && !newTitle.isBlank() && !Objects.equals(newTitle, existingEntity.getTitle()))
		{
			existingEntity.setTitle(newTitle);
		}

		return existingEntity;
	}

	public void delete(Long id)
	{
		repository.delete(repository.getReferenceById(id));
	}

	public Set<BudgetEntryDTO> findEntrisForCurrentMonth()
	{
		final LocalDate now = LocalDate.now();

		final LocalDate startOfMonth = now.with(firstDayOfMonth());
		final LocalDate endOfMonth = now.with(lastDayOfMonth());

		return repository.findEntriesForPeriod(startOfMonth, endOfMonth);
	}
}
