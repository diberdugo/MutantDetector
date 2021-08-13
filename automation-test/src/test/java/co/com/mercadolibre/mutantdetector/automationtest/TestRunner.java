package co.com.mercadolibre.mutantdetector.automationtest;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
@CucumberOptions(features = {"src/test/resources/features"},
        glue = {"co.com.mercadolibre.mutantdetector.automationtest.steps"},
        plugin = {"json:target/cucumber.json", "html:target/cucumber-reports",
                "junit:target/cucumber.xml"})
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
