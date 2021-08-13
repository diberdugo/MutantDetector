package co.com.mercadolibre.mutantdetector.service.strategy;

import java.util.stream.IntStream;

public class HorizontalDNA implements MutantDetectStrategy {

    /**
     * Obtains all horizontal DNA sequences
     * @param fullDNA Matrix will all the DNA
     * @return Quantity of found sequences of a mutant
     */
    @Override
    public Long execute(String[][] fullDNA) {
        return IntStream.range(0, fullDNA.length)
                .filter(i -> this.verifySequenceDNA(String.join("", fullDNA[i])))
                .count();
    }
}
