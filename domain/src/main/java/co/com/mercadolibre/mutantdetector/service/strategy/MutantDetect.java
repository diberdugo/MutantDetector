package co.com.mercadolibre.mutantdetector.service.strategy;

import java.util.ArrayList;
import java.util.List;

public class MutantDetect implements MutantDetectStrategy {

    private final List<MutantDetectStrategy> mutantDetectStrategies;

    public MutantDetect() {
        mutantDetectStrategies = new ArrayList<>();
        mutantDetectStrategies.add(new VerticalDNA());
        mutantDetectStrategies.add(new HorizontalDNA());
        mutantDetectStrategies.add(new ObliqueLeftDNA());
        mutantDetectStrategies.add(new ObliqueRightDNA());
    }

    @Override
    public Long execute(String[][] fullDNA) {
        return mutantDetectStrategies.stream()
                .mapToLong(strategy -> strategy.execute(fullDNA))
                .sum();
    }
}
