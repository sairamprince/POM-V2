package Test_Runner;

import org.testng.annotations.Test;

import base.Base;
import pageEvents.HomepageEvents;
import pageEvents.LoginpageEvents;
import utils.ElementFetch;

public class TestCase_1 extends Base
{
	ElementFetch element = new ElementFetch();
	HomepageEvents homeevent = new HomepageEvents();
	LoginpageEvents loginevent = new LoginpageEvents();
	@Test
	public void Testhome()
	{
		logger.info("Verify Sawg Labs Text preset or not");
		homeevent.verifyText();
	}
	@Test
	public void Testloginpage()
	{
		logger.info("Enter user crdentials");
		loginevent.entercredentials();
	}
}
