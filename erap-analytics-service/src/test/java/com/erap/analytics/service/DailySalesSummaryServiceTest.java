package com.erap.analytics.service;

import com.erap.analytics.dto.DailySalesSummaryResponse;
import com.erap.analytics.dto.KpiResponse;
import com.erap.analytics.dto.PagedResponse;
import com.erap.analytics.mapper.DailySalesSummaryMapper;
import com.erap.analytics.model.DailySalesSummary;
import com.erap.analytics.repository.DailySalesSummaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailySalesSummaryServiceTest {

    @Mock
    private DailySalesSummaryRepository repository;

    @Mock
    private DailySalesSummaryMapper mapper;

    @InjectMocks
    private DailySalesSummaryService service;

    @Test
    void getDailySales_shouldReturnPagedResponse() {
        DailySalesSummary data = new DailySalesSummary();
        data.setSummaryId(UUID.randomUUID());
        data.setDate(LocalDate.now());
        data.setAvgOrderValue(BigDecimal.valueOf(523.50));
        data.setTopCategory("Washing Machine");
        data.setTotalRevenue(BigDecimal.valueOf(78523.55));
        data.setTotalOrders(500);

        DailySalesSummaryResponse response = DailySalesSummaryResponse.builder()
                .summaryId(data.getSummaryId())
                .date(data.getDate())
                .totalOrders(data.getTotalOrders())
                .totalRevenue(data.getTotalRevenue())
                .avgOrderValue(data.getAvgOrderValue())
                .topCategory(data.getTopCategory())
                .build();

        Pageable pageable = PageRequest.of(0,30);

        Page<DailySalesSummary> page = new PageImpl<>(
                List.of(data), pageable, 1
        );

        when(repository.findAll(pageable)).thenReturn(page);
        when(mapper.toResponse(data)).thenReturn(response);

        PagedResponse<DailySalesSummaryResponse> dailySales = service.getDailySales(pageable);

        assertEquals(1, dailySales.getContent().size());
        assertEquals(response, dailySales.getContent().get(0));
        assertEquals(1, dailySales.getPage().getTotalElements());
        assertEquals(0, dailySales.getPage().getNumber());
    }

    @Test
    void getKpis() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate startOfMonth = today.withDayOfMonth(1);

        DailySalesSummary todayData = new DailySalesSummary();
        todayData.setSummaryId(UUID.randomUUID());
        todayData.setDate(LocalDate.now());
        todayData.setAvgOrderValue(BigDecimal.valueOf(523.50));
        todayData.setTopCategory("Washing Machine");
        todayData.setTotalRevenue(BigDecimal.valueOf(78523.55));
        todayData.setTotalOrders(500);

        List<DailySalesSummary> weekData = List.of(todayData);
        List<DailySalesSummary> monthData = List.of(todayData);

        when(repository.findByDate(today))
                .thenReturn(Optional.of(todayData));
        when(repository.findByDateBetween(startOfWeek, today))
                .thenReturn(weekData);
        when(repository.findByDateBetween(startOfMonth, today))
                .thenReturn(monthData);

        KpiResponse kpis = service.getKpis();

        assertEquals(500, kpis.getTotalOrdersToday());
        assertEquals(BigDecimal.valueOf(78523.55), kpis.getRevenueToday());
        assertEquals(500, kpis.getTotalOrdersThisWeek());
        assertEquals(BigDecimal.valueOf(78523.55), kpis.getRevenueThisWeek());
        assertEquals(500, kpis.getTotalOrdersThisMonth());
        assertEquals(BigDecimal.valueOf(78523.55), kpis.getRevenueThisMonth());
    }
}