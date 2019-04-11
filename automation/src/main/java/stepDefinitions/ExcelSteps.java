package stepDefinitions;

import java.util.List;

import com.automation.customclasses.Headers;
import com.automation.implementations.ExcelHeaderImplementation;

import cucumber.api.java.en.Given;

public class ExcelSteps {


@Given("^I am writing the following headers to the \"([^\"]*)\" \"([^\"]*)\" excel file:$")
public void i_am_writing_the_following_headers_to_the_excel_file(String sheetName, String fileName, List<Headers> headers) throws Throwable {
	ExcelHeaderImplementation impl = new ExcelHeaderImplementation();
    impl.writeToExcel(fileName, headers, sheetName);
}

	
}
