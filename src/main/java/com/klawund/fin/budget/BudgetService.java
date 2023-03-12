package com.klawund.fin.budget;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import com.klawund.fin.budget.dto.BasicBudgetDTO;
import com.klawund.fin.entry.EntryService;
import com.klawund.fin.entry.dto.BudgetEntryDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService
{
	private final EntryService entryService;

	public BasicBudgetDTO buildCurrentMonthBudget()
	{
		final LocalDate now = LocalDate.now();
		final LocalDate start = now.with(firstDayOfMonth());
		final LocalDate end = now.with(lastDayOfMonth());

		final Set<BudgetEntryDTO> entries = entryService.findEntriesForPeriod(start, end);
		final BigDecimal sum = sumEntries(entries);

		return BasicBudgetDTO.builder()
			.startDate(start)
			.endDate(end)
			.entries(entries)
			.sum(sum)
			.build();
	}

	public BigDecimal sumEntries(Set<BudgetEntryDTO> entries)
	{
		return entries.stream().map(BudgetEntryDTO::getAmmount).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
