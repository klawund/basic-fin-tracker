package com.klawund.fin.summary;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import com.klawund.fin.category.CategoryRepository;
import com.klawund.fin.category.dto.CategoryAggregateSumDTO;
import com.klawund.fin.entry.EntryService;
import com.klawund.fin.summary.dto.BasicSummaryDTO;
import com.klawund.fin.summary.dto.SummaryGroupedByCategoryDTO;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryService
{
	private final EntryService entryService;
	private final CategoryRepository categoryRepository;

	public BasicSummaryDTO buildCurrentMonthSummary()
	{
		final LocalDate now = LocalDate.now();
		final LocalDate start = now.with(firstDayOfMonth());
		final LocalDate end = now.with(lastDayOfMonth());

		return BasicSummaryDTO.builder()
			.startDate(start)
			.endDate(end)
			.sum(entryService.sumEntriesAmmountForPeriod(start, end))
			.build();
	}

	public SummaryGroupedByCategoryDTO buildCurrentMonthSummaryGroupedByCategory()
	{
		final LocalDate now = LocalDate.now();
		final LocalDate start = now.with(firstDayOfMonth());
		final LocalDate end = now.with(lastDayOfMonth());

		List<CategoryAggregateSumDTO> categoryAggregateSums = categoryRepository.findCategoryAggreagateSumsForPeriod(start, end);
		return SummaryGroupedByCategoryDTO.builder()
			.startDate(start)
			.endDate(end)
			.categorySummaries(categoryAggregateSums)
			.sum(entryService.sumEntriesAmmountForPeriod(start, end))
			.build();
	}
}
