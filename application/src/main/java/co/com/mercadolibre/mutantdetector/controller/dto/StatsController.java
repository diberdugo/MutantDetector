package co.com.mercadolibre.mutantdetector.controller.dto;

import co.com.mercadolibre.mutantdetector.controller.StatsResponse;
import co.com.mercadolibre.mutantdetector.data.MutantStatsDTO;
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
    public ResponseEntity<StatsResponse> getStatus() {
        MutantStatsDTO dto = mutantService.getStatus();
        return ResponseEntity.ok()
                .body(StatsResponse.builder()
                        .humans(dto.getHumans())
                        .mutants(dto.getMutants())
                        .ratio(dto.getRatio())
                        .build());
    }
}
