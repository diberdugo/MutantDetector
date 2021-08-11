package co.com.mercadolibre.mutantdetector.service.strategy;

import java.util.stream.IntStream;

public class HorizontalDNA implements MutantDetectStrategy {

    @Override
    public Long execute(String[][] fullDNA) {
        return IntStream.range(0, fullDNA.length)
                .filter(i -> verifySequenceDNA(String.join("", fullDNA[i])))
                .count();
    }
}
