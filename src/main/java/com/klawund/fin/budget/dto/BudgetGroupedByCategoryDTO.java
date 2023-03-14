package com.klawund.fin.budget.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klawund.fin.entry.dto.GroupedBudgetEntriesDTO;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetGroupedByCategoryDTO implements BaseBudgetDTO<Set<GroupedBudgetEntriesDTO>>, Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal sum;

	@JsonProperty("groupedEntries")
	private Set<GroupedBudgetEntriesDTO> entries;
}
