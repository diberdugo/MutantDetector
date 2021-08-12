package co.com.mercadolibre.mutantdetector.ports;

import co.com.mercadolibre.mutantdetector.data.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.exception.IncompleteDNAException;
import co.com.mercadolibre.mutantdetector.exception.InvalidDNACodeException;

public interface MutantServicePort {

    boolean isMutant(String[] dna) throws IncompleteDNAException, InvalidDNACodeException;

    MutantStatsDTO getStats();
}
