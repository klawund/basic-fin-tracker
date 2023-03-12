package com.klawund.fin.entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TenantId;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entry implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private LocalDate due;

	@Column(nullable = false)
	private BigDecimal ammount;

	@TenantId
	@JsonIgnore
	private String tenantId;

	@PrePersist
	private void prePersist()
	{
		if (due == null)
		{
			due = LocalDate.now();
		}
	}
}
