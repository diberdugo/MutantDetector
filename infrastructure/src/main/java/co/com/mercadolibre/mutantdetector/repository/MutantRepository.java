package co.com.mercadolibre.mutantdetector.repository;

import co.com.mercadolibre.mutantdetector.entity.Mutant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MutantRepository extends MongoRepository<Mutant, String> {
}
