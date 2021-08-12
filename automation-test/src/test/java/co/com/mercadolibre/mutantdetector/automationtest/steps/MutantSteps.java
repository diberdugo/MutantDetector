package co.com.mercadolibre.mutantdetector.automationtest.steps;

import co.com.mercadolibre.mutantdetector.automationtest.data.Mutant;
import io.cucumber.java8.En;

public class MutantSteps implements En {

    private final CommonsSteps commonsSteps;

    public MutantSteps(CommonsSteps commonsSteps) {
        this.commonsSteps = commonsSteps;

        When("I want to validate if the {string} DNA is a mutant", (String dna) -> {
            String[] dnaSequence = "null".equals(dna) ? null : dna.trim().split(",");

            commonsSteps.setResponse(commonsSteps.getRequest()
                    .when()
                    .body(Mutant.builder().dna(dnaSequence).build())
                    .post("/mutant"));
        });
    }
}
