package br.com.trainlab.trainlab.controller;

import br.com.trainlab.trainlab.dto.stats.StatsDto;
import br.com.trainlab.trainlab.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService service;

    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @GetMapping
    public ResponseEntity<StatsDto> getStats() {

        String email = getAuthenticatedUserEmail();

        StatsDto response = service.getStats(email);
        return ResponseEntity.ok(response);
    }
}
