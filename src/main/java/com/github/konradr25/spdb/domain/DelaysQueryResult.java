package com.github.konradr25.spdb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DelaysQueryResult {
    private long startStopId;
    private long stopStopId;
    private double totalDelay;
    private double sectionDelay;
}
