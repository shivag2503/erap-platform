package com.erap.analytics.repository;

import com.erap.analytics.model.DailySalesSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DailySalesSummaryRepository extends JpaRepository<DailySalesSummary, UUID> {

    Optional<DailySalesSummary> findByDate(LocalDate date);
    List<DailySalesSummary> findByDateBetween(LocalDate start, LocalDate end);
}
