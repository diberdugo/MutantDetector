package co.com.mercadolibre.mutantdetector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MutantStatsResponse {

    @JsonProperty(value = "count_human_dna")
    private Integer humans;

    @JsonProperty(value = "count_mutant_dna")
    private Integer mutants;

    private Double ratio;
}
