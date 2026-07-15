package com.example.bds.dto.Response.Listing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response cho search endpoint — khớp với ApiPagedResponse<ListingResponse> bên FE:
 * { code, message, content: [], metadata: { page, size, total, fallback } }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedListingResponse {
    private int code;
    private String message;
    private List<ListingResponse> content;
    private PageMetadata metadata;
}
