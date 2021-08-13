package co.com.mercadolibre.mutantdetector.service;

import co.com.mercadolibre.mutantdetector.dto.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.exception.IncompleteDNAException;
import co.com.mercadolibre.mutantdetector.exception.InvalidDNACodeException;
import co.com.mercadolibre.mutantdetector.ports.MutantPersistencePort;
import co.com.mercadolibre.mutantdetector.ports.MutantServicePort;
import co.com.mercadolibre.mutantdetector.service.strategy.HorizontalDNA;
import co.com.mercadolibre.mutantdetector.service.strategy.MutantDetect;
import co.com.mercadolibre.mutantdetector.service.strategy.ObliqueLeftDNA;
import co.com.mercadolibre.mutantdetector.service.strategy.ObliqueRightDNA;
import co.com.mercadolibre.mutantdetector.service.strategy.VerticalDNA;
import com.google.inject.internal.util.ImmutableSet;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MutantServiceImpl implements MutantServicePort {

    private final Set<String> DNACode = ImmutableSet.of("A", "T", "C", "G");

    private final MutantPersistencePort mutantPersistencePort;

    public MutantServiceImpl(MutantPersistencePort mutantPersistencePort) {
        this.mutantPersistencePort = mutantPersistencePort;
    }

    @Override
    public boolean isMutant(String[] dna) throws IncompleteDNAException, InvalidDNACodeException {
        MutantDetect mutantDetect = MutantDetect.builder()
                .mutantDetect(new VerticalDNA())
                .mutantDetect(new HorizontalDNA())
                .mutantDetect(new ObliqueLeftDNA())
                .mutantDetect(new ObliqueRightDNA())
                .build();

        boolean isMutant = mutantDetect.execute(buildFullDNA(dna)) > mutantDetect.MAX_SEQUENCE;
        mutantPersistencePort.saveMutant(dna, isMutant);

        return isMutant;
    }

    @Override
    public MutantStatsDTO getStats() {
        return mutantPersistencePort.getStats();
    }

    /**
     * Build a matrix with all DNA sequences
     * @param dna Array with all DNA sequences
     * @return Matrix with the complete DNA
     * @throws IncompleteDNAException if the DNA is incomplete
     * @throws InvalidDNACodeException if the DNA not hast only the four words allowed
     */
    private String[][] buildFullDNA(String[] dna) throws IncompleteDNAException, InvalidDNACodeException {
        verifyDNA(dna);

        String[][] fullDNA = new String[dna[0].length()][dna.length];
        IntStream.range(0, fullDNA.length)
                .forEach(i -> IntStream.range(0, fullDNA[i].length)
                        .forEach(j -> fullDNA[i][j] = String.valueOf(dna[i].charAt(j))));


        return fullDNA;
    }

    /**
     * Verify the DNA mutant is valid
     * @param dna Array with all DNA sequences
     * @throws IncompleteDNAException if the DNA is incomplete
     * @throws InvalidDNACodeException if the DNA not hast only the four words allowed
     */
    private void verifyDNA(String[] dna) throws IncompleteDNAException, InvalidDNACodeException {
        if (dna == null || Arrays.asList(dna).contains(null)) {
            throw new IncompleteDNAException("The DNA must not be null");
        }

        Set<String> currentDNACode = Arrays.stream(dna)
                .flatMap(i -> i.chars().mapToObj(c -> String.valueOf((char) c)))
                .collect(Collectors.toSet());

        if (!DNACode.containsAll(currentDNACode)) {
            throw new InvalidDNACodeException(String.format("Invalid DNA code, the only DNA code allowed is: '%s'",
                    DNACode.toString()));
        }

        long sequences = Arrays.stream(dna)
                .filter(seq -> seq.length() == dna.length)
                .count();

        if (sequences != dna.length) {
            throw new IncompleteDNAException("Unable to build the DNA, the DNA sequences are incomplete");
        }
    }
}
