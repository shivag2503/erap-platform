package com.erap.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailySalesSummaryResponse {

    private UUID summaryId;
    private LocalDate date;
    private Integer totalOrders;
    private BigDecimal totalRevenue;
    private BigDecimal avgOrderValue;
    private String topCategory;
    private LocalDateTime createdAt;
}