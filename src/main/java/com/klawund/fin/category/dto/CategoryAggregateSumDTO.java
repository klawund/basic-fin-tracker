package com.klawund.fin.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klawund.fin.category.Category;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAggregateSumDTO implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	public CategoryAggregateSumDTO(Category category, BigDecimal sum)
	{
		if (category != null)
		{
			this.categoryName = category.getName();
		}
		this.sum = sum;
	}

	@JsonProperty("category")
	private String categoryName;
	private BigDecimal sum;
}
