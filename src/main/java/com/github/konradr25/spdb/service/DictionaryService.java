package com.github.konradr25.spdb.service;

import com.github.konradr25.spdb.domain.LineDTO;
import com.github.konradr25.spdb.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DictionaryService {
    @Autowired
    private LineRepository lineRepository;

    public List<LineDTO> getAllLineNames() {
        return lineRepository.getAllLinesNames().stream().map(LineDTO::new).collect(Collectors.toList());
    }
}
