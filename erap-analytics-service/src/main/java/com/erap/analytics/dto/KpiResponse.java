package com.erap.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KpiResponse {

    private Integer totalOrdersToday;
    private BigDecimal revenueToday;
    private Integer totalOrdersThisWeek;
    private BigDecimal revenueThisWeek;
    private Integer totalOrdersThisMonth;
    private BigDecimal revenueThisMonth;
}