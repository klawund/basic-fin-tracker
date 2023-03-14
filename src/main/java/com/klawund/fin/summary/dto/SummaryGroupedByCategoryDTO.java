package com.klawund.fin.summary.dto;

import com.klawund.fin.category.dto.CategoryAggregateSumDTO;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryGroupedByCategoryDTO implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal sum;
	private List<CategoryAggregateSumDTO> categorySummaries;
}
