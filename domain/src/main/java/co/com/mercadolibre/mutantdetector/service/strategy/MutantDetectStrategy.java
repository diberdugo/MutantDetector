package co.com.mercadolibre.mutantdetector.service.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public interface MutantDetectStrategy {

    int MATCH_SEQUENCE = 4;

    default boolean verifySequenceDNA(String dna) {
        AtomicInteger max = new AtomicInteger(1);
        List<String> splitDNA = Arrays.asList(dna.split(""));

        IntStream.range(1, splitDNA.size()).forEach(i -> {
            int count = 1;
            while (i < splitDNA.size() && splitDNA.get(i - 1).equals(splitDNA.get(i))) {
                count++;
                i++;
                max.set(Math.max(count, max.get()));
            }
        });

        return max.get() == MATCH_SEQUENCE;
    }

    Long execute(String[][] fullDNA);
}
