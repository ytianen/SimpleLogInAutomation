package fox;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features ="classpath:features/"
		,monochrome = true
		,format="pretty"
		,glue={"fox/steps/"}
		,tags="@test"
		,plugin={"html:target/cucumber-html-report"}
				)

public class RunTest{
	
}