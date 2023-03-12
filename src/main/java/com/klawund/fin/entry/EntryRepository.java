package com.klawund.fin.entry;

import com.klawund.fin.entry.dto.BudgetEntryDTO;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntryRepository extends JpaRepository<Entry, Long>
{
	@Query("SELECT new com.klawund.fin.entry.dto.BudgetEntryDTO(e.title, e.ammount, e.due) FROM Entry e WHERE e.due >= ?1 AND e.due <= ?2")
	Set<BudgetEntryDTO> findEntriesForPeriod(LocalDate start, LocalDate end);
}
