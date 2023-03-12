package com.klawund.fin.entry;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(name = "EntryIdSeq", sequenceName = "ENTRY_ID_SEQ", allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entry implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EntryIdSeq")
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private LocalDate due;

	@Column(nullable = false)
	private BigDecimal ammount;

	@PrePersist
	private void prePersist()
	{
		if (due == null)
		{
			due = LocalDate.now();
		}
	}
}
