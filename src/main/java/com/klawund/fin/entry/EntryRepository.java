package com.klawund.fin.entry;

import com.klawund.fin.category.Category;
import com.klawund.fin.entry.dto.BudgetEntryDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntryRepository extends JpaRepository<Entry, Long>
{
	@Query("SELECT new com.klawund.fin.entry.dto.BudgetEntryDTO(e.title, e.ammount, e.due, e.category) FROM Entry e WHERE e.due >= ?1 AND e.due <= ?2")
	Set<BudgetEntryDTO> findEntriesForPeriod(LocalDate start, LocalDate end);

	@Query("SELECT SUM(e.ammount) FROM Entry e WHERE e.due >= ?1 AND e.due <= ?2")
	BigDecimal sumEntriesAmmountForPeriod(LocalDate start, LocalDate end);

	@Query("SELECT SUM(e.ammount) FROM Entry e WHERE e.due >= ?1 AND e.due <= ?2 AND e.category.name = ?3")
	BigDecimal sumEntriesAmmountForPeriodAndCategoryName(LocalDate start, LocalDate end, String categoryName);
}
