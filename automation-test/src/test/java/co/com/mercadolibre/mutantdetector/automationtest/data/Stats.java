package co.com.mercadolibre.mutantdetector.automationtest.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stats {

    @JsonProperty(value = "count_human_dna")
    private Integer humans;

    @JsonProperty(value = "count_mutant_dna")
    private Integer mutants;

    private Double ratio;
}
