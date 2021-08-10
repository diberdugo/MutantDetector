package co.com.mercadolibre.mutantdetector.service;

import co.com.mercadolibre.mutantdetector.data.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.exception.MutantException;
import co.com.mercadolibre.mutantdetector.ports.api.MutantServicePort;
import co.com.mercadolibre.mutantdetector.ports.spi.MutantPersistencePort;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MutantServiceImpl implements MutantServicePort {

    private final MutantPersistencePort mutantPersistencePort;

    public MutantServiceImpl(MutantPersistencePort mutantPersistencePort) {
        this.mutantPersistencePort = mutantPersistencePort;
    }

    @Override
    public boolean isMutant(String[] dna) throws MutantException {
        String[][] adn = buildADN(dna);

        Long countHorizontal = IntStream.range(0, adn.length)
                .filter(i -> validateADN(Arrays.asList(adn[i])))
                .count();

        Long countVertical = IntStream.range(0, adn.length)
                .filter(i -> validateADN(Arrays.stream(adn)
                        .map(row -> row[i])
                        .collect(Collectors.toList())))
                .count();

        Long countMainDiag = validateADN(IntStream.range(0, adn.length)
                .mapToObj(i -> adn[i][i])
                .collect(Collectors.toList())) ? 1L : 0L;

        Long countSecondaryDiag = validateADN(IntStream.range(0, adn.length)
                .mapToObj(i -> adn[i][adn.length - 1 - i])
                .collect(Collectors.toList())) ? 1L : 0L;

        boolean isMutant = countHorizontal + countVertical + countMainDiag + countSecondaryDiag > 1;
        mutantPersistencePort.saveMutant(dna, isMutant);

        return isMutant;
    }

    @Override
    public MutantStatsDTO getStatus() {
        return mutantPersistencePort.getStatus();
    }

    private String[][] buildADN(String[] dna) throws MutantException {
        String[][] adn = new String[6][6];

        try {
            IntStream.range(0, adn.length)
                    .forEach(i -> IntStream.range(0, adn[i].length)
                            .forEach(j -> adn[i][j] = String.valueOf(dna[i].charAt(j))));
        } catch (NullPointerException | StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
            throw new MutantException(e.getMessage(), e);
        }

        return adn;
    }

    private boolean validateADN(List<String> adn) {
        AtomicInteger max = new AtomicInteger(1);

        IntStream.range(1, adn.size()).forEach(i -> {
            int count = 1;
            while (i < adn.size() && adn.get(i - 1).equals(adn.get(i))) {
                count++;
                i++;
                max.set(Math.max(count, max.get()));
            }
        });

        return max.get() == 4;
    }
}
