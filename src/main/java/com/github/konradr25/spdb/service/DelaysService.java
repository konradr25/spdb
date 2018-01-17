package com.github.konradr25.spdb.service;

import com.github.konradr25.spdb.domain.DelaysDTO;
import com.github.konradr25.spdb.domain.DelaysQueryResult;
import com.github.konradr25.spdb.repository.DelaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DelaysService {
    @Autowired
    private DelaysRepository delaysRepository;

    public List<DelaysDTO> countDelays(String lineName, String start, String stop) {
        List<DelaysQueryResult> delaysQueryResults = delaysRepository.countDelays(lineName, start, stop);
        return mapToDelaysDTO(delaysQueryResults);
    }

    private List<DelaysDTO> mapToDelaysDTO(List<DelaysQueryResult> delaysQueryResults) {
        return delaysQueryResults.stream().map(delaysQueryResult -> new DelaysDTO(
                Double.toString(delaysQueryResult.getStopLatitude()),
                Double.toString(delaysQueryResult.getStopLongitude()),
                getSectionDelay(delaysQueryResult.getSectionDelay())
                )).collect(Collectors.toList());
    }

    /**
     * Gets section delay. If delay is negative it is replaced with 1.
     *
     * @param sectionDelay
     * @return delay
     */
    private double getSectionDelay(double sectionDelay) {
        if (sectionDelay <= 1) {
            return 1;
        } else {
            return sectionDelay;
        }
    }
}
