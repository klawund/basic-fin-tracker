package com.klawund.fin.entry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEntryDTO implements Serializable
{
	private String title;
	private LocalDate due;
	private BigDecimal ammount;

	@JsonProperty("category")
	private String categoryName;
}
