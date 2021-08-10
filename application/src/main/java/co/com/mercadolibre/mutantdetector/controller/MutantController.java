package co.com.mercadolibre.mutantdetector.controller;

import co.com.mercadolibre.mutantdetector.dto.MutantRequest;
import co.com.mercadolibre.mutantdetector.exception.MutantException;
import co.com.mercadolibre.mutantdetector.ports.api.MutantServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    @Autowired
    private MutantServicePort mutantService;

    @PostMapping({"/", ""})
    public ResponseEntity<Void> isMutant(@RequestBody MutantRequest mutantRequest) {
        try {
            return mutantService.isMutant(mutantRequest.getDna()) ? ResponseEntity.ok().build() :
                    ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (MutantException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}