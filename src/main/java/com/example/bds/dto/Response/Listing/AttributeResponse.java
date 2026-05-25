package com.example.bds.dto.Response.Listing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeResponse {
    private String code;
    private String description;
    private String label;
    private String attributeType;
    private String attributeValue;
    private String attributeStorage;
}
