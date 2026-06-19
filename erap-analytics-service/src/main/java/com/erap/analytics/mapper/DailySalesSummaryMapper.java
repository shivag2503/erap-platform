package com.erap.analytics.mapper;

import com.erap.analytics.dto.DailySalesSummaryResponse;
import com.erap.analytics.model.DailySalesSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DailySalesSummaryMapper {

    DailySalesSummaryResponse toResponse(DailySalesSummary dailySalesSummary);
}
