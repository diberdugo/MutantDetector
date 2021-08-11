package co.com.mercadolibre.mutantdetector.service.strategy;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public interface MutantDetectStrategy {

    int MATCH_SEQUENCE = 4;

    default boolean verifySequenceDNA(List<String> dna){
        AtomicInteger max = new AtomicInteger(1);

        IntStream.range(1, dna.size()).forEach(i -> {
            int count = 1;
            while (i < dna.size() && dna.get(i - 1).equals(dna.get(i))) {
                count++;
                i++;
                max.set(Math.max(count, max.get()));
            }
        });

        return max.get() == MATCH_SEQUENCE;
    }

    Long execute(String[][] fullDNA);
}
