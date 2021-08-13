package co.com.mercadolibre.mutantdetector.ports;

import co.com.mercadolibre.mutantdetector.dto.MutantStatsDTO;

public interface MutantPersistencePort {

    void saveMutant(String[] dna, boolean isMutant);

    MutantStatsDTO getStats();
}
