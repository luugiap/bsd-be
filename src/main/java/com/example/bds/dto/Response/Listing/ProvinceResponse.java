package com.example.bds.dto.Response.Listing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceResponse {

    private String province;

    private List<DistrictResponse> districts;
}
