package tests;

import com.denysord88.Main;
import com.denysord88.appium.AppiumAndroidInstance;
import com.denysord88.appium.AppiumiOSInstance;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobilePlatform;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;

import static com.denysord88.Main.SCREENSHOTS_FULL_PATH;
import static com.denysord88.appium.AppiumInstance.devices;

public class BaseTest {
    private static String deviceInfo;

    @BeforeTest
    @Parameters({"wdaPort", "simulatorPort", "OSVersion", "deviceName", "platform"})
    public void setUpAppium(String wdaPort, String simulatorPort, String OSVersion, String deviceName, String platform) {
        System.out.println("[TA_FE] (" + Main.stopwatch + ") @BeforeTest with params: wdaPort=" + wdaPort + " simulatorPort=" + simulatorPort +
                " OSVersion='" + OSVersion + "' deviceName='" + deviceName + "' platform='" + platform + "'");
        if(platform.equals(MobilePlatform.IOS)) {
            System.out.println("[TA_FE] (" + Main.stopwatch + ") Initialising iOS driver");
            initiOSDriver(wdaPort, simulatorPort, OSVersion, deviceName);
            System.out.println("[TA_FE] (" + Main.stopwatch + ") iOS driver initialised");
        } else if (platform.equals(MobilePlatform.ANDROID)) {
            System.out.println("[TA_FE] (" + Main.stopwatch + ") Initialising Android driver");
            initAndroidDriver(wdaPort, simulatorPort, OSVersion, deviceName);
            System.out.println("[TA_FE] (" + Main.stopwatch + ") Android driver initialised");
        }
    }

    @AfterTest
    @Parameters({"OSVersion", "deviceName", "platform"})
    public void tearDownSimulator(String OSVersion, String deviceName, String platform) {
        if(platform.equals(MobilePlatform.IOS)) {
            ((AppiumiOSInstance) devices.get(OSVersion + deviceName)).teardown();
        } else if(platform.equals(MobilePlatform.ANDROID)) {

        }
    }

    @AfterSuite
    public void tearDownAllSimulators() {
        System.out.println("_______________________________________________" +
                "\nTotal elapsed time:" +
                "\n" +
                Main.stopwatch +
                "\n_______________________________________________");
        deleteAlliOSSimulators();
    }

    public static AppiumDriver getDriver() {
        String OSVersion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("OSVersion");
        String deviceName = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("deviceName");
        return devices.get(OSVersion + deviceName).getDriver();
    }

    private static void initiOSDriver(String wdaPort, String simulatorPort, String iOSVersion, String deviceName) {
        AppiumiOSInstance ai = new AppiumiOSInstance(wdaPort, simulatorPort, iOSVersion, deviceName);
        devices.put(iOSVersion + deviceName, ai);
        deviceInfo = "Device_" + deviceName.replaceAll(" ", "_") +
                "_OS_v" + iOSVersion + "_";
    }

    private static void initAndroidDriver(String wdaPort, String simulatorPort, String androidVersion, String deviceName) {
        AppiumAndroidInstance ai = new AppiumAndroidInstance(wdaPort, simulatorPort, androidVersion, deviceName);
        devices.put(androidVersion + deviceName, ai);
        deviceInfo = "Device_" + deviceName.replaceAll(" ", "_") +
                "_OS_v" + androidVersion + "_";
    }

    public static void deleteAlliOSSimulators() {
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

    public static void checkAndFixScreenshotsPath() {
        String[] pathTree = SCREENSHOTS_FULL_PATH.split("/");
        String currentLevel = "/";
        for (String s : pathTree) {
            currentLevel += s + "/";
            new File(currentLevel).mkdir();
        }
    }
}

