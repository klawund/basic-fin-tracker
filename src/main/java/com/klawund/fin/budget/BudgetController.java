package com.klawund.fin.budget;

import com.klawund.fin.budget.dto.BasicBudgetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("budgets")
@RequiredArgsConstructor
public class BudgetController
{
	private final BudgetService service;

	@GetMapping("current")
	public ResponseEntity<BasicBudgetDTO> getCurrentMonthBudget()
	{
		return ResponseEntity.ok(service.buildCurrentMonthBudget());
	}
}
