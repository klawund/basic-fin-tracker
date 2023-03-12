package com.klawund.fin.budget.dto;

import java.math.BigDecimal;

public interface BaseBudgetDTO<T>
{
	T getEntries();

	BigDecimal getSum();
}
