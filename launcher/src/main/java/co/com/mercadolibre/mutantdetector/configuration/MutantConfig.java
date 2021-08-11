package co.com.mercadolibre.mutantdetector.configuration;

import co.com.mercadolibre.mutantdetector.adapters.MutantMongoAdapter;
import co.com.mercadolibre.mutantdetector.ports.MutantServicePort;
import co.com.mercadolibre.mutantdetector.ports.MutantPersistencePort;
import co.com.mercadolibre.mutantdetector.service.MutantServiceImpl;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
public class MutantConfig {

    @Autowired
    private MongoClient mongoClient;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public MutantPersistencePort mutantPersistence() {
        return new MutantMongoAdapter();
    }

    @Bean
    public MutantServicePort mutantService() {
        return new MutantServiceImpl(mutantPersistence());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, database);
        MappingMongoConverter converter = (MappingMongoConverter) mongoTemplate.getConverter();
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.afterPropertiesSet();

        return mongoTemplate;
    }
}
