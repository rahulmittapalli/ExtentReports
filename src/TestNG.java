import org.testng.annotations.*;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.awt.*;

public class TestNG {
    @BeforeMethod
    public void beforemethod()
    {
        System.out.println("This is fpr Before Method");
    }
    @BeforeSuite
    public void beforesuite()
    {
        System.out.println("This is for before suite");
    }
    @BeforeTest
    public void beforeTest()
    {
        System.out.println("This is for Before Test");
    }
    @BeforeClass
    public void beforeclass()
    {
        System.out.println("This is for Before class");
    }

    @Test
    public void test()
    {
        System.out.println("This is Actual Test");
    }
    @AfterMethod
    public void afterMethod()
    {
        System.out.println("This is for After Method");
    }
    @AfterClass
    public void afterClass()
    {
        System.out.println("This is for After Class");
    }
    @AfterTest
    public void afterTest()
    {
        System.out.println("This is for After Test");
    }
    @AfterSuite
    public void afterSuite()
    {
        System.out.println("This is for After Suite");
    }
}
