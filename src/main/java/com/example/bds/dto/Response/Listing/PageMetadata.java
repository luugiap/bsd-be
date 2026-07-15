package com.example.bds.dto.Response.Listing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageMetadata {
    private int page;
    private int size;
    private long total;
    private boolean fallback;
}
