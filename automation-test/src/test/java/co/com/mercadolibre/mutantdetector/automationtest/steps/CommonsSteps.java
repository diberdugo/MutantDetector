package co.com.mercadolibre.mutantdetector.automationtest.steps;

import io.cucumber.java8.En;
import io.cucumber.java8.Scenario;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.testng.Assert.assertEquals;

public class CommonsSteps implements En {

    private final OutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outputStream, true);
    private final RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(printStream);
    private final ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(printStream);
    @Getter
    private RequestSpecification request;
    @Getter
    private Response response;
    private Scenario scenario;

    public CommonsSteps() {
        Before((Scenario scenario) -> this.scenario = scenario);

        Given("I configured the mutants API", () -> {
            String baseUri = System.getProperty("uri") != null ? System.getProperty("uri") : "http://localhost:80/mutant-detector";
            request = RestAssured
                    .given()
                    .filters(requestLoggingFilter, responseLoggingFilter)
                    .contentType(ContentType.JSON)
                    .baseUri(baseUri);
        });

        Then("I will receipt HTTP code {int} as service response", (Integer code) ->
                assertEquals(response.getStatusCode(), code.intValue()));
    }

    public void setResponse(Response response) {
        this.response = response;
        scenario.log(outputStream.toString());
    }
}
