package com.erap.analytics.service;

import com.erap.analytics.dto.DailySalesSummaryResponse;
import com.erap.analytics.dto.KpiResponse;
import com.erap.analytics.dto.PagedResponse;
import com.erap.analytics.mapper.DailySalesSummaryMapper;
import com.erap.analytics.model.DailySalesSummary;
import com.erap.analytics.repository.DailySalesSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailySalesSummaryService {

    private final DailySalesSummaryRepository dailySalesSummaryRepository;
    private final DailySalesSummaryMapper dailySalesSummaryMapper;

    public PagedResponse<DailySalesSummaryResponse> getDailySales(Pageable pageable) {
        Page<DailySalesSummary> page  = dailySalesSummaryRepository.findAll(pageable);
        return new PagedResponse<>(
                page.getContent().stream()
                        .map(dailySalesSummaryMapper::toResponse)
                        .toList(),
                new PagedResponse.PageMetadata(page.getNumber(),
                        page.getSize(),
                        page.getTotalElements(),
                        page.getTotalPages(),
                        page.isFirst(),
                        page.isLast())
        );

    }

    public KpiResponse getKpis() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate startOfMonth = today.withDayOfMonth(1);

        Optional<DailySalesSummary> todayData = dailySalesSummaryRepository.findByDate(today);
        List<DailySalesSummary> weekData = dailySalesSummaryRepository.findByDateBetween(startOfWeek, today);
        List<DailySalesSummary> monthData = dailySalesSummaryRepository.findByDateBetween(startOfMonth, today);

        int totalOrdersToday = todayData.map(DailySalesSummary::getTotalOrders).orElse(0);
        BigDecimal revenueToday = todayData.map(DailySalesSummary::getTotalRevenue)
                .orElse(BigDecimal.ZERO);

        int totalOrdersThisWeek = weekData.stream().mapToInt(DailySalesSummary::getTotalOrders).sum();
        BigDecimal revenueThisWeek = weekData.stream().map(DailySalesSummary::getTotalRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalOrdersThisMonth = monthData.stream().mapToInt(DailySalesSummary::getTotalOrders).sum();
        BigDecimal revenueThisMonth = monthData.stream().map(DailySalesSummary::getTotalRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return KpiResponse.builder()
                .totalOrdersToday(totalOrdersToday)
                .revenueToday(revenueToday)
                .totalOrdersThisWeek(totalOrdersThisWeek)
                .revenueThisWeek(revenueThisWeek)
                .totalOrdersThisMonth(totalOrdersThisMonth)
                .revenueThisMonth(revenueThisMonth)
                .build();
    }
}