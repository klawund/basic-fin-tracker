package com.klawund.fin.entry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UpdateEntryDTO implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private LocalDate due;
	private BigDecimal ammount;
	@JsonProperty("category")
	private String categoryName;
}
