package org.example.spendwiseapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.spendwiseapi.dto.MonthlySummaryResponse;
import org.example.spendwiseapi.service.SummaryService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping("/monthly")
    public MonthlySummaryResponse monthly(
            @RequestParam Integer year,
            @RequestParam Integer month,
            Authentication authentication
    ) {
        return summaryService.getMonthlySummary(year, month, authentication);
    }
}