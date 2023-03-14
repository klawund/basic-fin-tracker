package com.klawund.fin.entry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class GroupedBudgetEntriesDTO implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	private LocalDate startDate;
	private LocalDate endDate;
	private String categoryName;
	private BigDecimal sum;

	@JsonProperty("category")
	private Set<BudgetEntryDTO> entries;
}
