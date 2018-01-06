package com.github.konradr25.spdb.controller;

import com.github.konradr25.spdb.domain.DelaysDTO;
import com.github.konradr25.spdb.service.DelaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("/delays")
public class DelaysController {
    @Autowired
    private DelaysService delaysService;

    @GetMapping
    public ResponseEntity<List<DelaysDTO>> countDelays(@PathVariable("lineName") String lineName,
                                                       @PathVariable(value = "start")
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime start,
                                                       @PathVariable(value = "stop")
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime stop) {
        return ResponseEntity.ok(delaysService.countDelays(lineName, start, stop));
    }
}