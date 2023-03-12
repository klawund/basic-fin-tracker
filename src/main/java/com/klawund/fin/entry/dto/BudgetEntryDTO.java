package com.klawund.fin.entry.dto;

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

	private String title;
	private BigDecimal ammount;
	private LocalDate due;
}
