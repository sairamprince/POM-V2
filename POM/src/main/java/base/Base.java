package base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.constants;

public class Base 
{
	public static WebDriver driver;
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	@BeforeTest
	public void beforeTestMethod()
	{
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator+"reports"+File.separator+"POMExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		sparkReporter.config().setTheme(Theme.DARK);
		extent.setSystemInfo("Hostname", "POM");
		extent.setSystemInfo("username", "sai ram");
		sparkReporter.config().setDocumentTitle("POM Web Automation");;
		sparkReporter.config().setReportName("Automation Tets Results by Sai Ram");
	}
	
	//This method is to capture the screenshot and return the path of the screenshot
	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException{
		String dateName = new SimpleDateFormat("yyyy/mm/dd-hh-mm-ss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		//after execution, you could see a folder "Screenshots" under src folder
		String destination = System.getProperty("user.dir")+"/screenshots/"+dateName+"FiledTestcase.png";
		File finaldestination = new File(destination);
		FileUtils.copyFile(source, finaldestination);
		return destination;
	}
	@BeforeMethod
	@Parameters("browser")
	public void beforeMethodTest(String browser,Method testMethod)
	{
		logger = extent.createTest(testMethod.getName());
		setupDriver(browser);
		driver.manage().window().maximize();
		driver.get(constants.url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException
	{
		if(result.getStatus() == ITestResult.FAILURE) 
		{
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+"   --FAIL-  ", ExtentColor.RED));
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable()+"   --FAIL--  ", ExtentColor.RED));
			String screenshotpath = getScreenshot(driver, result.getName());
			logger.fail("Test Case Failed Snapshot   "+logger.addScreenCaptureFromPath(screenshotpath));
		}
		else if(result.getStatus() == ITestResult.SKIP)
		{
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+"   --SKIPPED--  ", ExtentColor.ORANGE));
		}
		else if(result.getStatus() == ITestResult.SUCCESS)
		{
			logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+"   --PASS--  ", ExtentColor.GREEN));
			//driver.quit();
		}
	}

	@AfterTest
	public void afterTest()
	{
		extent.flush();
	}

	public void setupDriver(String browser)
	{
		if(browser.contentEquals("chrome")) 
		{
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if (browser.contentEquals("firefox")) 
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if (browser.contentEquals("edge"))
		{
			WebDriverManager.chromedriver().setup();
			driver = new EdgeDriver();
		}

	}
}





