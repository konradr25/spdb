package com.github.konradr25.spdb.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class LineRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String GET_ALL_LINE_NAMES_QUERY = "select name from csipline;";

    @Cacheable
    public List<String> getAllLinesNames() {
        return jdbcTemplate.query(GET_ALL_LINE_NAMES_QUERY, new Object[]{},
                (resultSet, i) -> resultSet.getString("name"));
    }
}
