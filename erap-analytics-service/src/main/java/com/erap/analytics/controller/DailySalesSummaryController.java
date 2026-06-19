package com.erap.analytics.controller;

import com.erap.analytics.dto.DailySalesSummaryResponse;
import com.erap.analytics.dto.KpiResponse;
import com.erap.analytics.dto.PagedResponse;
import com.erap.analytics.service.DailySalesSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
@Slf4j
public class DailySalesSummaryController {

    private final DailySalesSummaryService dailySalesSummaryService;

    @GetMapping("/sales/daily")
    public ResponseEntity<PagedResponse<DailySalesSummaryResponse>> getDailySales(
            @PageableDefault(size = 30, sort = "date", direction = Sort.Direction.DESC)
            Pageable pageable) {
        log.info("GET /api/v1/analytics/sales/daily called — page: {}, size: {}, sort: {}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        PagedResponse<DailySalesSummaryResponse> dailySales = dailySalesSummaryService.getDailySales(pageable);

        log.info("GET /api/v1/analytics/sales/daily completed — returned {} records",
                dailySales.getContent().size());

        return ResponseEntity.ok(dailySales);
    }

    @GetMapping("/kpis")
    public ResponseEntity<KpiResponse> getKpis() {
        log.info("GET /api/v1/analytics/kpis called");

        KpiResponse kpis = dailySalesSummaryService.getKpis();

        log.info("GET /api/v1/analytics/kpis completed — todayOrders: {}, weekOrders: {}, monthOrders: {}",
                kpis.getTotalOrdersToday(), kpis.getTotalOrdersThisWeek(), kpis.getTotalOrdersThisMonth());

        return ResponseEntity.ok(kpis);
    }
}