package co.com.mercadolibre.mutantdetector.service;

import co.com.mercadolibre.mutantdetector.data.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.exception.IncompleteDNAException;
import co.com.mercadolibre.mutantdetector.ports.MutantServicePort;
import co.com.mercadolibre.mutantdetector.ports.MutantPersistencePort;
import co.com.mercadolibre.mutantdetector.service.strategy.MutantDetect;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class MutantServiceImpl implements MutantServicePort {

    private final MutantPersistencePort mutantPersistencePort;

    public MutantServiceImpl(MutantPersistencePort mutantPersistencePort) {
        this.mutantPersistencePort = mutantPersistencePort;
    }

    @Override
    public boolean isMutant(String[] dna) throws IncompleteDNAException {
        Objects.requireNonNull(dna, "The DNA is required");
        String[][] fullDNA = buildFullDNA(dna);

        boolean isMutant = new MutantDetect().execute(fullDNA) > 1;

        mutantPersistencePort.saveMutant(dna, isMutant);
        return isMutant;
    }

    @Override
    public MutantStatsDTO getStatus() {
        return mutantPersistencePort.getStatus();
    }

    private String[][] buildFullDNA(String[] dna) throws IncompleteDNAException {
        String[][] fullDNA;

        long sequences = Arrays.stream(dna)
                .filter(seq -> seq.length() == dna.length)
                .count();

        if (sequences != dna.length) {
            throw new IncompleteDNAException("Unable to build the DNA, the DNA is incomplete");
        }
        else {
            fullDNA = new String[dna[0].length()][dna.length];
            IntStream.range(0, fullDNA.length)
                    .forEach(i -> IntStream.range(0, fullDNA[i].length)
                            .forEach(j -> fullDNA[i][j] = String.valueOf(dna[i].charAt(j))));
        }

        return fullDNA;
    }
}
