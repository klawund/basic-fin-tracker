package com.klawund.fin.summary;

import static java.time.temporal.TemporalAdjusters.*;

import com.klawund.fin.entry.EntryRepository;
import com.klawund.fin.summary.dto.BasicSummaryDTO;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryService
{
	private final EntryRepository entryRepository;

	public BasicSummaryDTO buildCurrentMonthSummary()
	{
		final LocalDate now = LocalDate.now();
		final LocalDate start = now.with(firstDayOfMonth());
		final LocalDate end = now.with(lastDayOfMonth());

		return BasicSummaryDTO.builder()
			.startDate(start)
			.endDate(end)
			.sum(entryRepository.sumEntriesAmmountForPeriod(start, end))
			.build();
	}
}
