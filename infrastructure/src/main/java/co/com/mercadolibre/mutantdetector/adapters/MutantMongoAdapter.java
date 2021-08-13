package co.com.mercadolibre.mutantdetector.adapters;

import co.com.mercadolibre.mutantdetector.dto.MutantStatsDTO;
import co.com.mercadolibre.mutantdetector.entity.Mutant;
import co.com.mercadolibre.mutantdetector.entity.MutantStats;
import co.com.mercadolibre.mutantdetector.ports.MutantPersistencePort;
import co.com.mercadolibre.mutantdetector.repository.MutantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Arrays;
import java.util.Optional;

public class MutantMongoAdapter implements MutantPersistencePort {

    @Autowired
    private MutantRepository mutantRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void saveMutant(String[] dna, boolean isMutant) {
        mutantRepository.save(Mutant.builder()
                .dna(Arrays.toString(dna))
                .isMutant(isMutant)
                .build());
    }

    @Override
    public MutantStatsDTO getStats() {
        ConditionalOperators.Cond isMutant = ConditionalOperators.when(new Criteria("isMutant").is(true))
                .then(1).otherwise(0);

        ConditionalOperators.Cond isHuman = ConditionalOperators.when(new Criteria("isMutant").is(false))
                .then(1).otherwise(0);

        GroupOperation groupStage = Aggregation.group()
                .sum(isMutant).as("countMutants")
                .sum(isHuman).as("countHumans");

        Aggregation aggregation = Aggregation.newAggregation(groupStage);

        AggregationResults<MutantStats> orderAggregate = mongoOperations.aggregate(aggregation,
                "mutants", MutantStats.class);

        Optional<MutantStats> stats = orderAggregate.getMappedResults().stream().findFirst();
        MutantStatsDTO mutantStatsDTO;

        if (stats.isPresent()) {
            mutantStatsDTO = MutantStatsDTO.builder()
                    .humans(stats.get().getCountHumans())
                    .mutants(stats.get().getCountMutants())
                    .build();
        }
        else {
            mutantStatsDTO = MutantStatsDTO.builder()
                    .humans(0)
                    .mutants(0)
                    .build();
        }

        return mutantStatsDTO;
    }
}
