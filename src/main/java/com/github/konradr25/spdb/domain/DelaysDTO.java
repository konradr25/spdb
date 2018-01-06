package com.github.konradr25.spdb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DelaysDTO {
    private String x;
    private String y;
    private long delay;
}
