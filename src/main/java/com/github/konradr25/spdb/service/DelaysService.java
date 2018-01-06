package com.github.konradr25.spdb.service;

import com.github.konradr25.spdb.domain.DelaysDTO;
import com.github.konradr25.spdb.domain.DelaysQueryResult;
import com.github.konradr25.spdb.repository.DelaysRepository;
import com.github.konradr25.spdb.repository.LineRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DelaysService {
    @Autowired
    private DelaysRepository delaysRepository;

    public List<DelaysDTO> countDelays(String lineName, LocalDateTime start, LocalDateTime stop) {
        List<DelaysQueryResult> delaysQueryResults = delaysRepository.countDelays(lineName, start.toLocalTime(), stop.toLocalTime());
        return mapToDelaysDTO(delaysQueryResults);
    }

    private List<DelaysDTO> mapToDelaysDTO(List<DelaysQueryResult> delaysQueryResults) {


        return Lists.newArrayList(); //TODO
    }
}
