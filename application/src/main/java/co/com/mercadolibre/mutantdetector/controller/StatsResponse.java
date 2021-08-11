package co.com.mercadolibre.mutantdetector.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatsResponse {

    @JsonProperty(value = "count_human_dna")
    private Integer humans;

    @JsonProperty(value = "count_mutant_dna")
    private Integer mutants;

    private Double ratio;
}
