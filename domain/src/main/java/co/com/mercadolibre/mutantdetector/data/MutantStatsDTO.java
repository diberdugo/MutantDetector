package co.com.mercadolibre.mutantdetector.data;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Getter
@Builder
public class MutantStatsDTO {

    private final Integer humans;
    private final Integer mutants;
    private final Double ratio;

    public Double getRatio() {
        double ratio;
        if (Optional.ofNullable(humans).orElse(0) == 0 ||
                Optional.ofNullable(mutants).orElse(0) == 0) {
            ratio = 0;
        } else {
            ratio = BigDecimal.valueOf(mutants)
                    .divide(BigDecimal.valueOf(humans), 2, RoundingMode.HALF_UP)
                    .doubleValue();
        }

        return ratio;
    }
}
