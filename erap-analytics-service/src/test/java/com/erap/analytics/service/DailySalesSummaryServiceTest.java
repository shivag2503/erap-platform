package com.erap.analytics.service;

import com.erap.analytics.dto.DailySalesSummaryResponse;
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
import java.time.LocalDate;
import java.util.List;
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

}