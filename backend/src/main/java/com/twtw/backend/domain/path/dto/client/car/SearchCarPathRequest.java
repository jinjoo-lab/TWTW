package com.twtw.backend.domain.path.dto.client.car;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCarPathRequest {
    @NotBlank
    private String start;
    @NotBlank
    private String end;
    @NotNull
    private String way;
    private SearchPathOption option;
    private SearchPathFuel fuel;
    @Min(1)
    private int car;
}
