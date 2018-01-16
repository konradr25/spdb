package com.github.konradr25.spdb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DelaysDTO {
    private String x;
    private String y;
    private Double delay;
}
