package com.twtw.backend.domain.plan.dto.request;

import com.twtw.backend.domain.plan.dto.response.PlaceDetails;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SavePlanRequest {
    private UUID groupId;
    private PlaceDetails placeDetails;
}
