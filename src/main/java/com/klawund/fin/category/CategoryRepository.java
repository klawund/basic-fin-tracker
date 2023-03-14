package com.klawund.fin.category;

import com.klawund.fin.category.dto.CategoryAggregateSumDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long>
{
	Optional<Category> findCategoryByName(String name);

	@Query("SELECT new com.klawund.fin.category.dto.CategoryAggregateSumDTO(c, SUM(e.ammount)) FROM Entry e LEFT JOIN e.category c WHERE e.due >= ?1 AND e.due <= ?2 GROUP BY c")
	List<CategoryAggregateSumDTO> findCategoryAggreagateSumsForPeriod(LocalDate start, LocalDate end);
}
