package co.com.mercadolibre.mutantdetector.controller;

import co.com.mercadolibre.mutantdetector.data.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.dto.MutantStatsResponse;
import co.com.mercadolibre.mutantdetector.ports.api.MutantServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private MutantServicePort mutantService;

    @GetMapping({"/", ""})
    public ResponseEntity<MutantStatsResponse> getStatus() {
        MutantStatsDTO dto = mutantService.getStatus();
        return ResponseEntity.ok()
                .body(MutantStatsResponse.builder()
                        .humans(dto.getHumans())
                        .mutants(dto.getMutants())
                        .ratio(dto.getRatio())
                        .build());
    }
}
