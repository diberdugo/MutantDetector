package co.com.mercadolibre.mutantdetector.automationtest.steps;

import co.com.mercadolibre.mutantdetector.automationtest.dto.StatsDTO;
import io.cucumber.java8.En;

import static org.testng.Assert.assertNotNull;

public class StatsSteps implements En {

    private final CommonsSteps commonsSteps;

    public StatsSteps(CommonsSteps commonsSteps) {
        this.commonsSteps = commonsSteps;

        When("I want to get the mutant stats", () ->
                commonsSteps.setResponse(commonsSteps.getRequest()
                        .when()
                        .get("/stats")));

        Then("I should see the stats info on the response", () -> {
            StatsDTO statsDTO = commonsSteps.getResponse().as(StatsDTO.class);
            assertNotNull(statsDTO.getHumans(), "The humans cont must not be null.");
            assertNotNull(statsDTO.getMutants(),"The mutant cont must not be null.");
            assertNotNull(statsDTO.getRatio(),"The ratio must not be null.");
        });
    }
}
