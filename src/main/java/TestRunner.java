import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features="src/main/resources",
        glue= {"StepDefinitions"}

)

public class TestRunner extends AbstractTestNGCucumberTests {

}