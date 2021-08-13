package co.com.mercadolibre.mutantdetector.controller;

import co.com.mercadolibre.mutantdetector.controller.dto.StatsResponseDTO;
import co.com.mercadolibre.mutantdetector.dto.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.ports.MutantServicePort;
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
    public ResponseEntity<StatsResponseDTO> getStats() {
        MutantStatsDTO dto = mutantService.getStats();
        return ResponseEntity.ok()
                .body(StatsResponseDTO.builder()
                        .humans(dto.getHumans())
                        .mutants(dto.getMutants())
                        .ratio(dto.getRatio())
                        .build());
    }

    @GetMapping({"/test"})
    public String test() {
        return "test v3";
    }
}
