package co.com.mercadolibre.mutantdetector.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@Builder
@Document(collection = "mutants")
public class Mutant {

    @Id
    @Indexed
    private String adn;

    @Field(value = "isMutant", targetType = FieldType.BOOLEAN)
    private Boolean isMutant;
}
