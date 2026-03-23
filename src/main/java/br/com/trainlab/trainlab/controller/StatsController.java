package br.com.trainlab.trainlab.controller;

import br.com.trainlab.trainlab.dto.stats.StatsDto;
import br.com.trainlab.trainlab.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/stats")
public class StatsController {

    @Autowired
    private StatsService service;

    @GetMapping
    public ResponseEntity<StatsDto> getStats(@PathVariable Long userId){
        StatsDto response = service.getStats(userId);
        return ResponseEntity.ok(response);
    }
}
