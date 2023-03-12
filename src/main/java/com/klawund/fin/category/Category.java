package com.klawund.fin.category;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
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
public class Category implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	@TenantId
	private String tenantId;

	@PrePersist
	private void prePersist()
	{
		name = name.trim();
	}
}
