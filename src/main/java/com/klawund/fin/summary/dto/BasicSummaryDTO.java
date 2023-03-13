package com.klawund.fin.summary.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicSummaryDTO implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal sum;
}
