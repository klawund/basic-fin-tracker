package com.klawund.fin.entry;

import com.klawund.fin.category.Category;
import com.klawund.fin.category.CategoryRepository;
import com.klawund.fin.category.CategoryService;
import com.klawund.fin.category.dto.CreateCategoryDTO;
import com.klawund.fin.entry.dto.CreateEntryDTO;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntryService
{
	private final EntryRepository repository;
	private final CategoryRepository categoryRepository;
	private final CategoryService categoryService;

	public Entry create(CreateEntryDTO createEntryDTO)
	{
		final Entry.EntryBuilder entryBuilder = Entry.builder()
			.title(createEntryDTO.getTitle())
			.due(createEntryDTO.getDue())
			.ammount(createEntryDTO.getAmmount());

		final Category category = getCategoryFromCreateEntryDTO(createEntryDTO);
		if (category != null)
		{
			entryBuilder.category(category);
		}

		return repository.save(entryBuilder.build());
	}

	private Category getCategoryFromCreateEntryDTO(CreateEntryDTO createEntryDTO)
	{
		final String categoryName = createEntryDTO.getCategoryName();
		if (categoryName == null || categoryName.isBlank())
		{
			return null;
		}

		Optional<Category> existingCategoryOptional = categoryRepository.findCategoryByName(categoryName);
		if (existingCategoryOptional.isPresent())
		{
			return existingCategoryOptional.get();
		}
		CreateCategoryDTO createCategoryDTO = CreateCategoryDTO.builder()
			.name(categoryName)
			.build();

		return categoryService.create(createCategoryDTO);
	}

	@Transactional
	// FIXME does not support category change yet
	public Entry update(Entry newEntity)
	{
		final Entry existingEntity = repository.findById(newEntity.getId())
			.orElseThrow(() -> new EntityNotFoundException(String.format("No Entry found for id %s", newEntity.getId())));

		final BigDecimal newAmmount = newEntity.getAmmount();
		if (newAmmount != null && !Objects.equals(newAmmount, existingEntity.getAmmount()))
		{
			existingEntity.setAmmount(newAmmount);
		}

		final LocalDate newDue = newEntity.getDue();
		if (newDue != null && !Objects.equals(newDue, existingEntity.getDue()))
		{
			existingEntity.setDue(newDue);
		}

		final String newTitle = newEntity.getTitle();
		if (newTitle != null && !newTitle.isBlank() && !Objects.equals(newTitle, existingEntity.getTitle()))
		{
			existingEntity.setTitle(newTitle);
		}

		return existingEntity;
	}

	public void delete(Long id)
	{
		repository.delete(repository.getReferenceById(id));
	}
}
