package co.com.mercadolibre.mutantdetector.ports;

import co.com.mercadolibre.mutantdetector.data.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.exception.IncompleteDNAException;

public interface MutantServicePort {

    boolean isMutant(String[] dna) throws IncompleteDNAException;

    MutantStatsDTO getStatus();
}
