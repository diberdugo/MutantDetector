package co.com.mercadolibre.mutantdetector.service.strategy;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VerticalDNA implements MutantDetectStrategy {

    @Override
    public Long execute(String[][] fullDNA) {
        return IntStream.range(0, fullDNA.length)
                .filter(i -> this.verifySequenceDNA(Arrays.stream(fullDNA)
                        .map(row -> row[i])
                        .collect(Collectors.joining(""))))
                .count();
    }
}
