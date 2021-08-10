package co.com.mercadolibre.mutantdetector.ports.api;

import co.com.mercadolibre.mutantdetector.data.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.exception.MutantException;

public interface MutantServicePort {

    boolean isMutant(String[] dna) throws MutantException;

    MutantStatsDTO getStatus();
}
