package co.com.mercadolibre.mutantdetector.automationtest.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Mutant {

    private final String[] dna;
}
