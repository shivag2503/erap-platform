package com.erap.analytics.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "daily_sales_summary", schema = "erap_analytics")
@Data
@NoArgsConstructor
public class DailySalesSummary extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "summary_id")
    private UUID summaryId;

    @Column(name = "date", nullable = false, unique = true)
    private LocalDate date;

    @Column(name = "total_orders")
    private Integer totalOrders;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

    @Column(name = "avg_order_value")
    private BigDecimal avgOrderValue;

    @Column(name = "top_category")
    private String topCategory;
}