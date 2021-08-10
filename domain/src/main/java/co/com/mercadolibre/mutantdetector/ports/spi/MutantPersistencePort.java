package co.com.mercadolibre.mutantdetector.ports.spi;

import co.com.mercadolibre.mutantdetector.data.MutantStatsDTO;

public interface MutantPersistencePort {

    void saveMutant(String[] dna, boolean isMutant);

    MutantStatsDTO getStatus();
}
