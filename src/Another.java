import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class Another {

    AppiumDriver driver=null;
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
    @BeforeClass
    public void startAppium() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "4.4.2");
        //capabilities.setCapability("udid", "ZY2226MHT8");
        capabilities.setCapability("deviceName", "Sony");
        capabilities.setCapability("automationName", "sample1");
        capabilities.setCapability("noReset", "True");
        capabilities.setCapability("autoGrantPermissions", "True");
        capabilities.setCapability("autoAcceptAlerts", "True");
        //capabilities.setCapability("avd", "Nexus_6_API_22");
        File app = new File("/Users/rahulmittapalli/Downloads/WiFiAnalyzer.apk");
        capabilities.setCapability("app", app.getAbsolutePath());
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }
    @BeforeMethod
    public void launchApp()
    {
        driver.launchApp();
    }
    @Test(priority = 0)
    public void wifiCheck() throws InterruptedException {
        wifiChecking(driver);
        testInfo.log(Status.INFO,"Wifi is turned ON");
    }
    @Test(priority = 1)
    public void locationCheck() throws InterruptedException {
        locationChecking(driver);
        testInfo.log(Status.INFO,"Location is turned ON");

    }
    @Test(priority = 7)
    public void connectStatus() throws InterruptedException {
        connectedStatus(driver);
        testInfo.log(Status.INFO,"Status is Connected");
    }
    @Test(priority = 3)
    public void networkScan() throws InterruptedException {
        scanNetworks(driver);
        testInfo.log(Status.INFO,"NetworkStatus is Connected");
    }
    @Test(priority = 4)
    public void scanButton() throws InterruptedException {
        scanbuttonVisibility(driver);
        testInfo.log(Status.INFO,"ScanButton is disabled after 15 Seconds");
    }
    @Test(priority = 5)
    public void ssidInformation() throws InterruptedException {
        ssidInfo(driver);
        testInfo.log(Status.INFO,"ssid Information is Displayed ");
    }
    @Test(priority = 6)
    public void recommendInformation() throws InterruptedException {
        recommendationInfo(driver);
        testInfo.log(Status.INFO,"recommend Information is Displayed");
    }
    @Test(priority = 2)
    public void channelRatingsInfo() throws InterruptedException {
        channelRatings(driver);
        testInfo.log(Status.INFO,"ChannelRatings is checked");
    }
    @Test(priority = 8)
    public void channelGraph() throws InterruptedException {
        channelGraph(driver);
        testInfo.log(Status.INFO,"ChannelGraph is checked");
    }
    public void wifiChecking(AppiumDriver driver) throws InterruptedException {
        this.driver = driver;
        if (driver.findElements(By.id("com.app.wifianalyzer:id/wifi_enable_btn")).size() != 0) {
            System.out.println("Wifi is turned OFF");
            driver.findElement(By.id("com.app.wifianalyzer:id/wifi_enable_btn")).click();
            Thread.sleep(10000);
        } else {
            System.out.println("Wifi is turned ON");
        }
    }
    public void locationChecking(AppiumDriver driver) throws InterruptedException {
        this.driver = driver;
        if (driver.findElements(By.id("com.app.wifianalyzer:id/dialog_ok")).size() != 0) {
            System.out.println("Location is turned OFF");
            WebElement location = driver.findElement(By.id("com.app.wifianalyzer:id/dialog_ok"));
            System.out.println(location.isDisplayed());
            location.click();
            driver.findElement(By.id("com.android.settings:id/switch_widget")).click();
            Thread.sleep(3000);
        } else {
            System.out.println("Location is turned ON");
        }
        driver.closeApp();
    }
    public void connectedStatus(AppiumDriver driver) throws InterruptedException {
        this.driver=driver;
        driver.findElement(By.id("com.app.wifianalyzer:id/scan_other_networks")).click();
        WebElement mainlist = driver.findElement(By.id("com.app.wifianalyzer:id/refresh_scan_list"));
        List<WebElement> scanlist = mainlist.findElements(By.className("android.widget.FrameLayout"));
        System.out.println(scanlist.size());
        Thread.sleep(5000);
        for(int i=0;i<scanlist.size()-1;i++) {
            if (i == 0) {
                System.out.println("Connected Network is " + scanlist.get(i).findElement(By.id("com.app.wifianalyzer:id/network_connected_ssid")).getText());
            } else {
                System.out.println("Remaining Networks are " + scanlist.get(i).findElement(By.id("com.app.wifianalyzer:id/network_not_connected_ssid")).getText());
            }
        }
        Thread.sleep(3000);
        driver.findElement(By.id("com.app.wifianalyzer:id/show_recommendations_btn")).click();
        driver.findElement(By.className("android.widget.ImageButton")).click();
        String NetworkName=driver.findElement(By.id("com.app.wifianalyzer:id/current_network_name")).getText();
        System.out.println(NetworkName);
        String FrequencyName=driver.findElement(By.id("com.app.wifianalyzer:id/frequency_text")).getText();
        System.out.println(FrequencyName);
        String NetworkStatus=driver.findElement(By.id("com.app.wifianalyzer:id/network_connected_text")).getText();
        System.out.println(NetworkStatus);
        boolean imagevalue=driver.findElement(By.id("com.app.wifianalyzer:id/wifi_signal_level")).isDisplayed();
        if(imagevalue==true)
        {
            System.out.println("Network signal icon is present");
        }
        else
        {
            System.out.println("Network signal icon is Not present");
        }
        driver.closeApp();
    }
    public void scanNetworks(AppiumDriver driver)
    {
        this.driver=driver;
        driver.findElement(By.id("com.app.wifianalyzer:id/scan_other_networks")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/menu_add_network")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/dialog_cancel")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/menu_add_network")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/dialog_ok")).click();
        driver.closeApp();
    }
    public void scanbuttonVisibility(AppiumDriver driver) throws InterruptedException {
        this.driver = driver;
        driver.findElement(By.id("com.app.wifianalyzer:id/scan_other_networks")).click();
        WebElement mainlist = driver.findElement(By.id("com.app.wifianalyzer:id/refresh_scan_list"));
        List<WebElement> scanlist = mainlist.findElements(By.className("android.widget.FrameLayout"));
        scanlist.get(0).findElement(By.className("android.widget.RadioButton")).click();
        Thread.sleep(16000);
        System.out.println("Scan button status is "+driver.findElement(By.id("com.app.wifianalyzer:id/show_recommendations_btn")).isEnabled());
        driver.closeApp();
    }
    public void ssidInfo(AppiumDriver driver) throws InterruptedException {
        this.driver = driver;
        driver.findElement(By.id("com.app.wifianalyzer:id/scan_other_networks")).click();
        WebElement mainlist = driver.findElement(By.id("com.app.wifianalyzer:id/refresh_scan_list"));
        List<WebElement> scanlist = mainlist.findElements(By.className("android.widget.FrameLayout"));
        scanlist.get(0).findElement(By.className("android.widget.RadioButton")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/show_recommendations_btn")).click();
        Thread.sleep(5000);
        driver.launchApp();
    }
    public void recommendationInfo(AppiumDriver driver) throws InterruptedException {
        this.driver = driver;
        driver.findElement(By.id("com.app.wifianalyzer:id/scan_other_networks")).click();
        WebElement mainlist = driver.findElement(By.id("com.app.wifianalyzer:id/refresh_scan_list"));
        List<WebElement> scanlist = mainlist.findElements(By.className("android.widget.FrameLayout"));
        scanlist.get(0).findElement(By.className("android.widget.RadioButton")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/show_recommendations_btn")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/menu_rescan")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("com.app.wifianalyzer:id/channel_ratings_btn")).click();
        driver.findElement(By.className("android.widget.ImageButton")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/channel_graph_btn")).click();
        driver.closeApp();
    }
    public void channelRatings(AppiumDriver driver) throws InterruptedException {
        this.driver = driver;
        driver.findElement(By.id("com.app.wifianalyzer:id/scan_other_networks")).click();
        WebElement mainlist = driver.findElement(By.id("com.app.wifianalyzer:id/refresh_scan_list"));
        List<WebElement> scanlist = mainlist.findElements(By.className("android.widget.FrameLayout"));
        scanlist.get(0).findElement(By.className("android.widget.RadioButton")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/show_recommendations_btn")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/channel_ratings_btn")).click();
        Thread.sleep(3000);
        WebElement Text=driver.findElement(By.id("com.app.wifianalyzer:id/channel_rating_header"));
        System.out.println(Text.findElement(By.className("android.widget.TextView")).getText());
        List<WebElement> values = driver.findElements(By.className("android.support.v7.app.a$c"));
        System.out.println(values.size());
        values.get(1).click();
        WebElement FiveText = driver.findElement(By.id("com.app.wifianalyzer:id/five_ghz_not_supported"));
        System.out.println(FiveText.getText());
        Assert.assertEquals(FiveText.getText(), "This device does not support 5GHZ mode");
        values.get(0).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/menu_channel")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.app.wifianalyzer:id/dialog_ok")).click();
        driver.findElement(By.className("android.widget.ImageButton")).click();
        driver.closeApp();
    }


    public void channelGraph(AppiumDriver driver) throws InterruptedException {
        this.driver = driver;
        driver.findElement(By.id("com.app.wifianalyzer:id/scan_other_networks")).click();
        WebElement mainlist = driver.findElement(By.id("com.app.wifianalyzer:id/refresh_scan_list"));
        List<WebElement> scanlist = mainlist.findElements(By.className("android.widget.FrameLayout"));
        scanlist.get(0).findElement(By.className("android.widget.RadioButton")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/show_recommendations_btn")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/channel_graph_btn")).click();
        driver.findElement(By.id("com.app.wifianalyzer:id/menu_rescan")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("com.app.wifianalyzer:id/five_GHZ")).click();
        if (driver.findElements(By.id("com.app.wifianalyzer:id/next_channels")).size() != 0) {
            driver.findElement(By.id("com.app.wifianalyzer:id/next_channels")).click();
            Thread.sleep(3000);
            driver.findElement(By.id("com.app.wifianalyzer:id/next_channels")).click();
            Thread.sleep(3000);
            driver.findElement(By.id("com.app.wifianalyzer:id/next_channels")).click();
            Thread.sleep(3000);
            driver.findElement(By.id("com.app.wifianalyzer:id/next_channels")).click();
            Thread.sleep(3000);
            driver.findElement(By.id("com.app.wifianalyzer:id/next_channels")).click();
            Thread.sleep(3000);
            driver.findElement(By.id("com.app.wifianalyzer:id/prev_channels")).click();
            Thread.sleep(3000);
            driver.findElement(By.id("com.app.wifianalyzer:id/prev_channels")).click();
            Thread.sleep(3000);
            driver.findElement(By.id("com.app.wifianalyzer:id/prev_channels")).click();
            Thread.sleep(3000);
            driver.findElement(By.id("com.app.wifianalyzer:id/prev_channels")).click();
            Thread.sleep(3000);
            driver.findElement(By.id("com.app.wifianalyzer:id/prev_channels")).click();
            Thread.sleep(3000);
        } else {
            WebElement FiveText = driver.findElement(By.id("com.app.wifianalyzer:id/five_ghz_not_supported"));
            Assert.assertEquals(FiveText.getText(), "This device does not support 5GHZ mode");
        }
        driver.findElement(By.id("com.app.wifianalyzer:id/two_GHZ")).click();
        driver.findElement(By.className("android.widget.ImageButton")).click();
        driver.closeApp();
    }

    @BeforeMethod
    public void register(Method method)
    {
        String testName=method.getName();
        testInfo=reports.createTest(testName);
    }
    @AfterMethod
    public void status(ITestResult result) throws Exception {
        if(result.getStatus()==ITestResult.SUCCESS)
        {
            testInfo.log(Status.PASS,"The test method named as "+result.getName()+ " is passed");
        }
        else if(result.getStatus()==ITestResult.FAILURE)
        {
            testInfo.log(Status.FAIL,"The test method named as "+result.getName()+ " is failed");
            testInfo.log(Status.FAIL,"Testfailure"+ result.getThrowable());
            String screenshotPath = screenshot.getScreenshot(driver,"Screenshot");
            //To add it in the extent report
            testInfo.log(Status.FAIL, result.getThrowable());
            testInfo.log(Status.FAIL,"Snapshot below is "+testInfo.addScreenCaptureFromPath(screenshotPath));

        }
        else if(result.getStatus()==ITestResult.SKIP)
        {
            testInfo.log(Status.SKIP,"The test method named as "+result.getName()+ " is skipped");
        }
    }
    @AfterTest
    public void cleanup()
    {
        reports.flush();
    }
}
