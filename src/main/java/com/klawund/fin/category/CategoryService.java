package com.klawund.fin.category;

import com.klawund.fin.category.dto.CreateCategoryDTO;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService
{
	private final CategoryRepository repository;

	public Category create(CreateCategoryDTO createCategoryDTO)
	{
		final String name = createCategoryDTO.getName();
		if (repository.findCategoryByName(name).isPresent())
		{
			throw new EntityExistsException(String.format("There already is a registered Category with the name %s", name));
		}

		final Category newCategory = Category.builder()
			.name(createCategoryDTO.getName())
			.build();

		return repository.save(newCategory);
	}
}
