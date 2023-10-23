package com.twtw.backend.domain.place.entity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String placeName;

    private Integer distance;

    private String placeUrl;

    private String categoryName;

    private CategoryGroupCode categoryGroupCode;

    @Embedded private Address address;

    @Embedded private Coordinate coordinate;

    @Builder
    public Place(
            final String placeName,
            final Integer distance,
            final String placeUrl,
            final String categoryName,
            final CategoryGroupCode categoryGroupCode,
            final String addressName,
            final String roadAddressName,
            final String x,
            final String y) {
        this.placeName = placeName;
        this.distance = distance;
        this.placeUrl = placeUrl;
        this.categoryName = categoryName;
        this.categoryGroupCode = categoryGroupCode;
        this.address = new Address(addressName, roadAddressName);
        this.coordinate = new Coordinate(Double.valueOf(x), Double.valueOf(y));
    }
}