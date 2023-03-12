package com.klawund.fin.entry.dto;

import com.klawund.fin.category.Category;
import com.klawund.fin.category.dto.CategoryDTO;
import com.klawund.fin.entry.Entry;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetEntryDTO implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	public BudgetEntryDTO(String title, BigDecimal ammount, LocalDate due, Category category)
	{
		this.title = title;
		this.ammount = ammount;
		this.due = due;
		this.category = new CategoryDTO(category);
	}

	private String title;
	private BigDecimal ammount;
	private LocalDate due;
	private CategoryDTO category;
}
