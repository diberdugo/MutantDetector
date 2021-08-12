package co.com.mercadolibre.mutantdetector.automationtest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MutantDTO {

    private final String[] dna;
}
