WITH Delay_per_course as (
    SELECT
      daystop.delaysec - coalesce(lag(delaysec)
                                  OVER (
                                    PARTITION BY daystop.daycourse_loid
                                    ORDER BY daystop.scheduleddeparture ), 0) delay_on_section,
      stop.latitude                                                           end_latitude,
      stop.longitude                                                          end_longitude,
      lag(stop.loid)
      OVER (
        PARTITION BY daystop.daycourse_loid
        ORDER BY daystop.scheduleddeparture )                                 start_stopId
    FROM csipline line
      JOIN csipdaystopping daystop ON line.loid = daystop.line_loid
      JOIN csipstoppoint stop ON daystop.stoppoint_loid = stop.loid
    WHERE
      line.name = :Line
      AND (timezone('CET', daystop.realdeparture) - date_trunc('day', daystop.realdeparture))
      BETWEEN to_timestamp(:Start, 'YYYY-MM-DD HH24:MI:SS') -
                     date_trunc('day',
                         to_timestamp(:Start, 'YYYY-MM-DD HH24:MI:SS'
                         ))::TIMESTAMP
      and to_timestamp(:End, 'YYYY-MM-DD HH24:MI:SS') -
                     date_trunc('day',
                     to_timestamp(:End, 'YYYY-MM-DD HH24:MI:SS'
                     ))
      AND daystop.realdeparture IS NOT NULL
    ORDER BY
      daystop.daycourse_loid,
      daystop.realdeparture ASC
)
SELECT
  end_latitude,
  end_longitude,
  avg(delay_on_section) delay
from Delay_per_course
WHERE start_stopId is NOT NULL
GROUP BY   end_latitude, end_longitude