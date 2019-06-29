package testRunners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
//		        features="C:\\Users\\sivaa\\workspace\\automation\\resources\\features\\ContactUs.feature",
//				features = {"classpath:features"}, 
		features="C:\\Users\\sivaa\\git\\ExcelPrograms\\automation\\resources\\features\\excel.feature",
		
//				tags={"@ScenarioLevelTag1"},
				monochrome = true,
				glue="stepDefinitions", 
				dryRun = false)

public class LoginHRMRunner {
	

}
