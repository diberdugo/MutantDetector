package co.com.mercadolibre.mutantdetector.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class MutantRequest implements Serializable {

    private String[] dna;
}
