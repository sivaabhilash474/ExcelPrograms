package stepDefinitions;

import java.util.List;

import com.automation.pages.YETIContacUSFormPage;
import com.automation.testcases.YETIContactUSFormTest;
import com.automation.utilities.TypeField;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class ContactUSSteps {
	
	List<TypeField> formFieldGlobal;
	String email;
	
	@Given("^I am on the contact us page$")
	public void i_am_on_the_contact_us_page() throws Throwable {
		YETIContactUSFormTest.getPage();
	    
	}

	@And("^I entered the following details$")
	public void i_entered_the_following_details(List<TypeField> formfields) throws Throwable {
		formFieldGlobal = formfields;
		
		
		for(TypeField formfield: formfields)
		{
			
		email = formfield.getEmail();
		
		YETIContacUSFormPage.setFormFieldValue(formfield.getFname(), formfield.getLname(), formfield.getEmail(), 
				formfield.getConfirmEmail());
		}
		System.out.println("Email ID: "+ email);
		
		
		
	}

}
