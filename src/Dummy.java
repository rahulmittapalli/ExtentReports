import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;

public class Dummy {


    ExtentReports reports;
    ExtentTest testInfo;
    ExtentHtmlReporter htmlReporter;
    @BeforeTest

    public void setup(){
        htmlReporter =new ExtentHtmlReporter(new File(System.getProperty("user.dir")+"/AutomationReports.html"));
        htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir"))+"/extent-config.xml");
        reports=new ExtentReports();
        reports.setSystemInfo("Sample","QA");
        reports.attachReporter(htmlReporter);
    }
    @Test
    public void methodOne()
    {
        Assert.assertTrue(true);
        testInfo.log(Status.INFO,"This is sample Test One");
    }
    @Test
    public void methodTwo()
    {
        Assert.assertTrue(true);
        testInfo.log(Status.INFO,"This is sample Test Two");
    }
    @BeforeMethod
    public void register(Method method)
    {
        String testName=method.getName();
        testInfo=reports.createTest(testName);
    }
    @AfterMethod
    public void status(ITestResult result)
    {
        if(result.getStatus()==ITestResult.SUCCESS)
        {
            testInfo.log(Status.PASS,"The test method named as"+result.getName()+"is passed");
        }
        else if(result.getStatus()==ITestResult.FAILURE)
        {
            testInfo.log(Status.FAIL,"The test method named as"+result.getName()+"is failed");
            testInfo.log(Status.FAIL,"Testfailure"+result.getThrowable());
        }
        else if(result.getStatus()==ITestResult.SKIP)
        {
            testInfo.log(Status.SKIP,"The test method named as"+result.getName()+"is skipped");
        }
    }
    @AfterTest
    public void cleanup()
    {
        reports.flush();
    }
}
