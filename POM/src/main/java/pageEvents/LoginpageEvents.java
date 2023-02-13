package pageEvents;

import pageObjects.LoginpageElements;
import utils.ElementFetch;

public class LoginpageEvents 
{
	ElementFetch element = new ElementFetch();
	public void entercredentials()
	{
		element.getWebElement("XPATH", LoginpageElements.username).sendKeys("standard_user");
		element.getWebElement("XPATH", LoginpageElements.password).sendKeys("secret_sauce");
		element.getWebElement("XPATH", LoginpageElements.loginbutton).click();
	}

}
