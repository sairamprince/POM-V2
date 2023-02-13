package pageEvents;

import pageObjects.HomepageElements;
import utils.ElementFetch;

public class HomepageEvents 
{
	ElementFetch element = new ElementFetch();
	public void verifyText()
	{
		System.out.println(element.getWebElement("XPATH", HomepageElements.homepagetitle).isDisplayed());
	}
}
