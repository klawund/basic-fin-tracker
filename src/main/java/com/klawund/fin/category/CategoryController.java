package com.klawund.fin.category;

import com.klawund.fin.category.dto.CreateCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController
{
	private final CategoryService service;

	@PostMapping
	public ResponseEntity<Category> create(@RequestBody CreateCategoryDTO createCategoryDTO)
	{
		return ResponseEntity.ok(service.create(createCategoryDTO));
	}
}
