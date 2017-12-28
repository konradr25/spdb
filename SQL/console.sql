WITH Delay_per_course as (
    SELECT
      line.loid,
      daystop.daycourse_loid,
      line.name                                                               line_number,
      daystop.realdeparture,
      daystop.scheduleddeparture,
      daystop.delaysec                                                        total_delay,
      daystop.delaysec - coalesce(lag(delaysec)
                                  OVER (
                                    PARTITION BY daystop.daycourse_loid
                                    ORDER BY daystop.scheduleddeparture ), 0) delay_on_section,
      lag(stop.latitude)
      OVER (
        PARTITION BY daystop.daycourse_loid
        ORDER BY daystop.scheduleddeparture )                                 start_latitude,
      lag(stop.longitude)
      OVER (
        PARTITION BY daystop.daycourse_loid
        ORDER BY daystop.scheduleddeparture )                                 start_longitude,
      stop.latitude                                                           end_latitude,
      stop.longitude                                                          end_longitude,
      lag(stop.loid)
      OVER (
        PARTITION BY daystop.daycourse_loid
        ORDER BY daystop.scheduleddeparture )                                 start_stopId,
      stop.loid AS                                                            end_stopId,
      stop.name AS                                                            stopName
    FROM csipline line
      JOIN csipdaystopping daystop ON line.loid = daystop.line_loid
      JOIN csipstoppoint stop ON daystop.stoppoint_loid = stop.loid
    WHERE

      --             csipline.name = ? AND
      --      date_part('hour', csipdaystopping.scheduleddeparture) = ? AND
--      line.name = '22' AND
      daystop.realdeparture IS NOT NULL
    ORDER BY
      daystop.daycourse_loid,
      daystop.realdeparture ASC
)
SELECT start_stopId, end_stopId, avg(total_delay) total_delay, avg(delay_on_section) delay_on_section from Delay_per_course
WHERE start_stopId is NOT NULL
GROUP BY start_stopId, end_stopId