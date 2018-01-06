package com.github.konradr25.spdb.controller;

import com.github.konradr25.spdb.domain.LineDTO;
import com.github.konradr25.spdb.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping("/lineNames")
    public ResponseEntity<List<LineDTO>> getAllLineNames() {
        return ResponseEntity.ok(dictionaryService.getAllLineNames());
    }
}
