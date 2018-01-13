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

    private static final String GET_DELAYS_QUERY = "WITH Delay_per_course AS (\n" +
            "    SELECT\n" +
            "      daystop.delaysec - coalesce(lag(delaysec)\n" +
            "                                  OVER (\n" +
            "                                    PARTITION BY daystop.daycourse_loid\n" +
            "                                    ORDER BY daystop.scheduleddeparture ), 0) delay_on_section,\n" +
            "      stop.latitude                                                           end_latitude,\n" +
            "      stop.longitude                                                          end_longitude,\n" +
            "      lag(stop.loid)\n" +
            "      OVER (\n" +
            "        PARTITION BY daystop.daycourse_loid\n" +
            "        ORDER BY daystop.scheduleddeparture )                                 start_stopId\n" +
            "    FROM csipline line\n" +
            "      JOIN csipdaystopping daystop ON line.loid = daystop.line_loid\n" +
            "      JOIN csipstoppoint stop ON daystop.stoppoint_loid = stop.loid\n" +
            "    WHERE\n" +
            "      line.name = :Line\n" +
            "      AND (timezone('CET', daystop.realdeparture) - date_trunc('day', daystop.realdeparture))\n" +
            "      BETWEEN to_timestamp(:Start, 'YYYY-MM-DD HH24:MI:SS') -\n" +
            "                     date_trunc('day',\n" +
            "                         to_timestamp(:Start, 'YYYY-MM-DD HH24:MI:SS'\n" +
            "                         ))::TIMESTAMP\n" +
            "      AND to_timestamp(:End, 'YYYY-MM-DD HH24:MI:SS') -\n" +
            "                     date_trunc('day',\n" +
            "                     to_timestamp(:End, 'YYYY-MM-DD HH24:MI:SS'\n" +
            "                     ))\n" +
            "      AND daystop.realdeparture IS NOT NULL\n" +
            "    ORDER BY\n" +
            "      daystop.daycourse_loid,\n" +
            "      daystop.realdeparture ASC\n" +
            ")\n" +
            "SELECT\n" +
            "  end_latitude,\n" +
            "  end_longitude,\n" +
            "  avg(delay_on_section) delay\n" +
            "FROM Delay_per_course\n" +
            "WHERE start_stopId IS NOT NULL\n" +
            "GROUP BY   end_latitude, end_longitude";

    public List<DelaysQueryResult> countDelays(String lineName, String start, String stop) {
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
