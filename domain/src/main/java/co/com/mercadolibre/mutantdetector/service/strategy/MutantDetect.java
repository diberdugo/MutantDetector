package co.com.mercadolibre.mutantdetector.service.strategy;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class MutantDetect implements MutantDetectStrategy {

    private final List<MutantDetectStrategy> mutantDetectStrategies;

    /**
     * Execute all strategies to find the DNA mutant
     * @param fullDNA Matrix will all the DNA
     * @return Quantity of found sequences of a mutant
     */
    @Override
    public Long execute(String[][] fullDNA) {
        return mutantDetectStrategies.stream()
                .mapToLong(strategy -> strategy.execute(fullDNA))
                .sum();
    }

    public static class MutantDetectBuilder {

        public MutantDetectBuilder() {
            this.mutantDetectStrategies = new ArrayList<>();
        }

        public MutantDetectBuilder mutantDetect(MutantDetectStrategy mutantDetectStrategy) {
            this.mutantDetectStrategies.add(mutantDetectStrategy);
            return this;
        }
    }
}
