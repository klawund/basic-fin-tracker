package com.klawund.fin.summary;

import com.klawund.fin.summary.dto.BasicSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("summaries")
@RequiredArgsConstructor
public class SummaryController
{
	private final SummaryService service;

	@GetMapping("current")
	public ResponseEntity<BasicSummaryDTO> getCurrentMonthSummeary()
	{
		return ResponseEntity.ok(service.buildCurrentMonthSummary());
	}
}
