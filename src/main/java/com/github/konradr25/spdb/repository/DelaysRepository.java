package com.github.konradr25.spdb.repository;

import com.github.konradr25.spdb.domain.DelaysQueryResult;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DelaysRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String GET_DELAYS_QUERY = ""; //TODO query

    @Cacheable
    public List<DelaysQueryResult> countDelays(String lineName, LocalTime start, LocalTime stop) {
        return jdbcTemplate.query(GET_DELAYS_QUERY, new Object[]{lineName, start, stop}, getRowMapper());
    }

    private RowMapper<DelaysQueryResult> getRowMapper() {
        return (resultSet, i) -> DelaysQueryResult.builder()
                .sectionDelay(resultSet.getDouble("delay_on_section"))
                .totalDelay(resultSet.getDouble("total_delay"))
                //TODO
                .build();
    }
}
