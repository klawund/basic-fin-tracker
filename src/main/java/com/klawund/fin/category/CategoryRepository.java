package com.klawund.fin.category;

import com.klawund.fin.category.dto.CategoryAggregateSumDTO;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long>
{
	Optional<Category> findCategoryByName(String name);

	@Query("SELECT new com.klawund.fin.category.dto.CategoryAggregateSumDTO(e.category, SUM(e.ammount)) FROM Entry e WHERE e.due >= ?1 AND e.due <= ?2 GROUP BY e.category")
	Set<CategoryAggregateSumDTO> findCategoryAggreagateSumsForPeriod(LocalDate start, LocalDate end);
}
