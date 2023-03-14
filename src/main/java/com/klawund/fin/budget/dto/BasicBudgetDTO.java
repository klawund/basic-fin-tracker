package com.klawund.fin.budget.dto;

import com.klawund.fin.entry.dto.BudgetEntryDTO;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicBudgetDTO implements BaseBudgetDTO<List<BudgetEntryDTO>>, Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal sum;
	private List<BudgetEntryDTO> entries = new ArrayList<>();
}
