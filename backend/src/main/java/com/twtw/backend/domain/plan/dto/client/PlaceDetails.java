package com.twtw.backend.domain.plan.dto.client;

import com.twtw.backend.domain.place.entity.CategoryGroupCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDetails {
    private String placeName;
    private String distance;
    private String placeUrl;
    private String categoryName;
    private String addressName;
    private String roadAddressName;
    private CategoryGroupCode categoryGroupCode;
    private String x;
    private String y;
}