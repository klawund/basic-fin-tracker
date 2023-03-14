package com.klawund.fin.entry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntryRepository extends JpaRepository<Entry, Long>
{
	@Query("SELECT e FROM Entry e WHERE e.due >= ?1 AND e.due <= ?2")
	List<Entry> findEntriesForPeriod(LocalDate start, LocalDate end);

	@Query("SELECT SUM(e.ammount) FROM Entry e WHERE e.due >= ?1 AND e.due <= ?2")
	Optional<BigDecimal> sumEntriesAmmountForPeriod(LocalDate start, LocalDate end);

	@Query("SELECT SUM(e.ammount) FROM Entry e WHERE e.due >= ?1 AND e.due <= ?2 AND e.category.name = ?3")
	Optional<BigDecimal> sumEntriesAmmountForPeriodAndCategory(LocalDate start, LocalDate end, String categoryName);

	@Query("SELECT SUM(e.ammount) FROM Entry e WHERE e.due >= ?1 AND e.due <= ?2 AND e.category IS NULL")
	Optional<BigDecimal> sumEntriesAmmountWithoutCategoryForPeriod(LocalDate start, LocalDate end);
}
