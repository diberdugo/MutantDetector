package co.com.mercadolibre.mutantdetector.automationtest.steps;

import co.com.mercadolibre.mutantdetector.automationtest.data.Stats;
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
            Stats stats = commonsSteps.getResponse().as(Stats.class);
            assertNotNull(stats.getHumans(), "The humans cont must not be null.");
            assertNotNull(stats.getMutants(),"The mutant cont must not be null.");
            assertNotNull(stats.getRatio(),"The ratio must not be null.");
        });
    }
}
