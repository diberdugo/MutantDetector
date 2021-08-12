package co.com.mercadolibre.mutantdetector.controller.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class MutantRequestDTO implements Serializable {

    private String[] dna;
}
