package co.com.mercadolibre.mutantdetector.service;

import co.com.mercadolibre.mutantdetector.data.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.exception.IncompleteDNAException;
import co.com.mercadolibre.mutantdetector.exception.InvalidDNACodeException;
import co.com.mercadolibre.mutantdetector.ports.MutantPersistencePort;
import co.com.mercadolibre.mutantdetector.ports.MutantServicePort;
import co.com.mercadolibre.mutantdetector.service.strategy.MutantDetect;
import com.google.inject.internal.util.ImmutableSet;

import java.util.Arrays;
import java.util.Objects;
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
        Objects.requireNonNull(dna, "The DNA is required");

        boolean isMutant = new MutantDetect().execute(buildFullDNA(dna)) > 1;
        mutantPersistencePort.saveMutant(dna, isMutant);

        return isMutant;
    }

    @Override
    public MutantStatsDTO getStatus() {
        return mutantPersistencePort.getStatus();
    }

    private String[][] buildFullDNA(String[] dna) throws IncompleteDNAException, InvalidDNACodeException {
        String[][] fullDNA;

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
            throw new IncompleteDNAException("Unable to build the DNA, the DNA is incomplete");
        }

        fullDNA = new String[dna[0].length()][dna.length];
        IntStream.range(0, fullDNA.length)
                .forEach(i -> IntStream.range(0, fullDNA[i].length)
                        .forEach(j -> fullDNA[i][j] = String.valueOf(dna[i].charAt(j))));


        return fullDNA;
    }
}
