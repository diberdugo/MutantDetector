package co.com.mercadolibre.mutantdetector.service.strategy;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class MutantDetect implements MutantDetectStrategy {

    private final List<MutantDetectStrategy> mutantDetectStrategies;

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
