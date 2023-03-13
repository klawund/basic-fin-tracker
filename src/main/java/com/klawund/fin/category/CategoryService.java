package com.klawund.fin.category;

import com.klawund.fin.category.dto.CreateCategoryDTO;
import jakarta.persistence.EntityExistsException;
import java.util.Optional;
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

	public Category findCategoryByNameOrCreateIfAbsent(String categoryName)
	{
		if (categoryName == null || categoryName.isBlank())
		{
			return null;
		}

		Optional<Category> existingCategoryOptional = repository.findCategoryByName(categoryName);
		if (existingCategoryOptional.isPresent())
		{
			return existingCategoryOptional.get();
		}
		CreateCategoryDTO createCategoryDTO = CreateCategoryDTO.builder()
			.name(categoryName)
			.build();

		return create(createCategoryDTO);
	}
}
