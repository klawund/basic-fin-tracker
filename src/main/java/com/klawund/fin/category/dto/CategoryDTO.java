package com.klawund.fin.category.dto;

import com.klawund.fin.category.Category;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	public CategoryDTO(Category category)
	{
		if (category != null)
		{
			this.name = category.getName();
		}
	}

	private String name;
}
