package com.klawund.fin.entry.dto;

import com.klawund.fin.category.dto.CategoryDTO;
import com.klawund.fin.entry.Entry;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetEntryDTO implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	public BudgetEntryDTO(Entry entry)
	{
		this.title = entry.getTitle();
		this.ammount = entry.getAmmount();
		this.due = entry.getDue();
		this.category = new CategoryDTO(entry.getCategory());
	}

	private String title;
	private BigDecimal ammount;
	private LocalDate due;
	private CategoryDTO category;
}
