package tests;

import com.denysord88.appium.flutter.FlutterFinder;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static com.denysord88.conf.Configuration.*;
import static org.testng.Assert.assertEquals;

public class MyFirstTest {
    private AppiumDriverLocalService service;
    private IOSDriver driver;

    @BeforeTest
    public void setUpAppium() {
        System.out.println("!!! 1 Starting the Appium service");
        service = AppiumDriverLocalService.
                buildService(new AppiumServiceBuilder()
                        .usingPort(4200)
                        .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/")
                        .withArgument(() -> "--allow-insecure", "get_server_logs")
                        .withArgument(() -> "--log-level", "debug"));
        service.start();

        System.out.println("!!! 2 Creating Capabilities");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:noReset", false);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Flutter");

        // Logging:
        capabilities.setCapability(IOSMobileCapabilityType.SHOW_IOS_LOG, false);
        capabilities.setCapability(IOSMobileCapabilityType.SHOW_XCODE_LOG, true);
        capabilities.setCapability(MobileCapabilityType.ENABLE_PERFORMANCE_LOGGING, false);

        // Timeouts:
        capabilities.setCapability("webkitResponseTimeout", 5000);
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 15000);
        capabilities.setCapability(IOSMobileCapabilityType.WDA_CONNECTION_TIMEOUT, 15000);
        capabilities.setCapability(IOSMobileCapabilityType.IOS_INSTALL_PAUSE, 15000);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 5000);

        capabilities.setCapability(IOSMobileCapabilityType.USE_PREBUILT_WDA, true);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        if (IOS_SIMULATOR_UUID.isEmpty()) {
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "NewApp_TA_Pixel_5_Android_33");
        } else {
            capabilities.setCapability(MobileCapabilityType.UDID, IOS_SIMULATOR_UUID);
        }
        capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, 4000);
        capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, false);
        capabilities.setCapability(IOSMobileCapabilityType.AUTO_DISMISS_ALERTS, false);
        //capabilities.setCapability(MobileCapabilityType.APP, IOS_APP_PATH);
        //capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.denysord1988.pocapp");

        System.out.println("!!! 3 Starting the driver");
        driver = new IOSDriver(service.getUrl(), capabilities);
        //driver.executeScript("flutter:waitForFirstFrame");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    }

    @AfterTest
    public void tearDownSimulator() {
        if (service != null) {
            service.stop();
        }
    }

    @AfterSuite
    public void deleteAllSimulators() {
        try {
            String[] killAllAppiumServices = {"/bin/bash", "-c", "for pid in $(" +
                    "ps -A " +
                    "| grep appium " +
                    "| grep 'main.js --port ' " +
                    "| awk '{print $1}'" +
                    "); do kill -9 $pid; done"};
            Runtime.getRuntime().exec(killAllAppiumServices);

            String[] deleteAllAppiumSimulators = {"/bin/bash", "-c", "for udid in $(" +
                    "/usr/bin/xcrun simctl list " +
                    "| grep 'appiumTest-' " +
                    "| grep '(Booted)' " +
                    "| cut -d \"(\" -f2 " +
                    "| cut -d \")\" -f1" +
                    "); do /usr/bin/xcrun simctl delete $udid; done"};
            Runtime.getRuntime().exec(deleteAllAppiumSimulators);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test()
    public void accessTest() throws InterruptedException {
        System.out.println("!!! 4 Installing the application");
        driver.installApp(IOS_APP_PATH);
        Thread.sleep(5000);

        System.out.println("!!! 5 Starting the application");
        driver.activateApp("com.denysord1988.pocapp");
        Thread.sleep(5000);

        System.out.println("!!! 6 flutter:waitForFirstFrame");
        driver.executeScript("flutter:waitForFirstFrame");

        System.out.println("!!! 7 Creating FlutterFinder object");
        FlutterFinder finder = new FlutterFinder(driver);

//        driver.context("NATIVE_APP");
//        driver.findElements(By.xpath("//*[@label='']")).get(0).sendKeys("1234567890");
//        driver.context("FLUTTER");

        driver.executeScript("flutter:setTextEntryEmulation", true);
        finder.byValueKey("_TextFieldValueKey").sendKeys("0987654321");
        driver.executeScript("flutter:setTextEntryEmulation", false);

        driver.context("NATIVE_APP");
        String textFieldWithoutKey = driver.findElements(By.xpath("//*[@label='']")).get(0).getText();
        driver.context("FLUTTER");
        finder.byText("0987654321").click();

        assertEquals(textFieldWithoutKey, "1234567890");

        finder.byText("Check access").click();
        assertEquals(finder.byValueKey("_accessHeaderTitle").getText(), "Access");

        assertEquals(finder.byValueKey("_locationAccessStatusText").getText(), "press check");
        finder.byValueKey("_checkLocationAccessButton").click();
        assertEquals(finder.byValueKey("_locationAccessStatusText").getText(), "Restricted");
        finder.byValueKey("_requestLocationAccessButton").click();
        Thread.sleep(2000);
        driver.context("NATIVE_APP");
        driver.findElement(By.xpath("//*[@label='Allow While Using App']")).click();
        driver.context("FLUTTER");
        finder.byValueKey("_checkLocationAccessButton").click();
        Thread.sleep(1000);
        assertEquals(finder.byValueKey("_locationAccessStatusText").getText(), "Granted");

        assertEquals(finder.byValueKey("_photosAccessStatusText").getText(), "press check");
        finder.byValueKey("_checkPhotosAccessButton").click();
        assertEquals(finder.byValueKey("_photosAccessStatusText").getText(), "Restricted");
        finder.byValueKey("_requestPhotosAccessButton").click();
        Thread.sleep(2000);
        driver.context("NATIVE_APP");
        driver.findElement(By.xpath("//*[@label='Allow Access to All Photos']")).click();
        driver.context("FLUTTER");
        finder.byValueKey("_checkPhotosAccessButton").click();
        Thread.sleep(1000);
        assertEquals(finder.byValueKey("_photosAccessStatusText").getText(), "Granted");
        driver.context("NATIVE_APP");
        driver.findElement(By.xpath("//*[@label='Back']")).click();
        driver.context("FLUTTER");
        Thread.sleep(1000);
        finder.byText("Check access");
    }
}
