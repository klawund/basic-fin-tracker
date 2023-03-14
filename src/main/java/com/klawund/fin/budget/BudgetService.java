package com.klawund.fin.budget;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import com.klawund.fin.budget.dto.BasicBudgetDTO;
import com.klawund.fin.budget.dto.BudgetGroupedByCategoryDTO;
import com.klawund.fin.category.CategoryRepository;
import com.klawund.fin.entry.EntryService;
import com.klawund.fin.entry.dto.BudgetEntryDTO;
import com.klawund.fin.entry.dto.GroupedBudgetEntriesDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService
{
	private final EntryService entryService;
	private final CategoryRepository categoryRepository;

	public BasicBudgetDTO buildCurrentMonthBudget()
	{
		final LocalDate now = LocalDate.now();
		final LocalDate start = now.with(firstDayOfMonth());
		final LocalDate end = now.with(lastDayOfMonth());

		final List<BudgetEntryDTO> entries = entryService.findBudgetEntriesForPeriod(start, end);
		final BigDecimal sum = entryService.sumEntriesAmmountForPeriod(start, end);

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

		final BigDecimal sum = entryService.sumEntriesAmmountForPeriod(start, end);
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
		final List<BudgetEntryDTO> periodEntries = entryService.findBudgetEntriesForPeriod(start, end);
		final Map<String, Set<BudgetEntryDTO>> groupedEntries = groupEntriesByCategory(periodEntries);

		return groupedEntries.entrySet().stream().map(entryGroup -> {
			String categoryName = entryGroup.getKey();
			final Set<BudgetEntryDTO> entries = entryGroup.getValue();

			final BigDecimal groupSum = entryService.sumEntriesAmmountForPeriodAndCategory(start, end, categoryName);
			if (categoryName == null)
			{
				categoryName = "none";
			}

			return new GroupedBudgetEntriesDTO(start, end, categoryName, groupSum, entries);
		}).collect(Collectors.toSet());
	}

	private Map<String, Set<BudgetEntryDTO>> groupEntriesByCategory(List<BudgetEntryDTO> entries)
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
