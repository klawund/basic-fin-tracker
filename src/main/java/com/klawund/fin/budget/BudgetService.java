package com.klawund.fin.budget;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import com.klawund.fin.budget.dto.BasicBudgetDTO;
import com.klawund.fin.budget.dto.BudgetGroupedByCategoryDTO;
import com.klawund.fin.entry.EntryRepository;
import com.klawund.fin.entry.dto.BudgetEntryDTO;
import com.klawund.fin.entry.dto.GroupedBudgetEntriesDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService
{
	private final EntryRepository entryRepository;

	public BasicBudgetDTO buildCurrentMonthBudget()
	{
		final LocalDate now = LocalDate.now();
		final LocalDate start = now.with(firstDayOfMonth());
		final LocalDate end = now.with(lastDayOfMonth());

		final Set<BudgetEntryDTO> entries = entryRepository.findEntriesForPeriod(start, end);
		final BigDecimal sum = entryRepository.sumEntriesAmmountForPeriod(start, end);

		return BasicBudgetDTO.builder()
			.startDate(start)
			.endDate(end)
			.entries(entries)
			.sum(sum)
			.build();
	}

	public BudgetGroupedByCategoryDTO buildCurrentMonthBudgetGroupedByCategory()
	{
		final LocalDate now = LocalDate.now();
		final LocalDate start = now.with(firstDayOfMonth());
		final LocalDate end = now.with(lastDayOfMonth());

		final BigDecimal sum = entryRepository.sumEntriesAmmountForPeriod(start, end);
		final Set<GroupedBudgetEntriesDTO> entries = findEntitiesForPeriodGroupedByCategory(start, end);

		return BudgetGroupedByCategoryDTO.builder()
			.startDate(start)
			.endDate(end)
			.sum(sum)
			.entries(entries)
			.build();
	}

	private Set<GroupedBudgetEntriesDTO> findEntitiesForPeriodGroupedByCategory(LocalDate start, LocalDate end)
	{
		final Set<BudgetEntryDTO> periodEntries = entryRepository.findEntriesForPeriod(start, end);
		final Map<String, Set<BudgetEntryDTO>> groupedEntries = groupEntriesByCategory(periodEntries);

		return groupedEntries.entrySet().stream().map(entryGroup -> {
			String categoryName = entryGroup.getKey();
			final Set<BudgetEntryDTO> entries = entryGroup.getValue();

			final BigDecimal groupSum = entryRepository.sumEntriesAmmountForPeriodAndCategoryName(start, end, categoryName);
			if (categoryName == null)
			{
				categoryName = "none";
			}

			return new GroupedBudgetEntriesDTO(start, end, categoryName, entries, groupSum);
		}).collect(Collectors.toSet());
	}

	private Map<String, Set<BudgetEntryDTO>> groupEntriesByCategory(Set<BudgetEntryDTO> entries)
	{
		final Map<String, Set<BudgetEntryDTO>> groupedEntries = new HashMap<>();
		for (BudgetEntryDTO entry : entries)
		{
			Set<BudgetEntryDTO> computedEntriesForCategory = groupedEntries.computeIfAbsent(entry.getCategory().getName(), k -> new HashSet<>());
			computedEntriesForCategory.add(entry);
		}
		return groupedEntries;
	}
}
