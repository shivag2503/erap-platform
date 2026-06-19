package com.erap.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagedResponse<T> {

    private List<T> content;
    private PageMetadata page;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PageMetadata {

        private int number;
        private int size;
        private long totalElements;
        private int totalPages;
        private boolean first;
        private boolean last;
    }
}

