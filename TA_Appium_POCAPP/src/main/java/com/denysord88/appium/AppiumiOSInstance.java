package com.denysord88.appium;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.denysord88.conf.Configuration;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static com.denysord88.appium.AppiumController.runAppiumService;
import static com.denysord88.conf.Configuration.*;

public class AppiumiOSInstance extends AppiumInstance {

    public AppiumiOSInstance(String wdaPort, String simulatorPort, String iOSVersion, String deviceName) {
        service = runAppiumService(wdaPort);
        DesiredCapabilities capabilities = prepareiOSDriverCapabilities(simulatorPort, iOSVersion, deviceName);
        driver = new IOSDriver(service.getUrl(), capabilities);
        driver.executeScript("flutter:waitForFirstFrame");
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITLY_WAIT_SECONDS));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        //UDID = driver.getCapabilities().getCapability("udid").toString();
    }

    public IOSDriver getDriver() {
        return (IOSDriver) driver;
    }

    public void teardown() {
        if (service != null) {
            service.stop();
        }
        /*try {
            Runtime.getRuntime().exec("/usr/bin/xcrun simctl delete " + UDID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }

    private DesiredCapabilities prepareiOSDriverCapabilities(String simulatorPort, String iOSVersion, String deviceName) {
        DesiredCapabilities capabilities = super.prepareDriverCapabilities();
        //capabilities.setCapability(IOSMobileCapabilityType.SHOW_IOS_LOG, Configuration.ENABLE_LOGGING);
        //capabilities.setCapability(IOSMobileCapabilityType.SHOW_XCODE_LOG, Configuration.ENABLE_LOGGING);

        // Timeouts
        //capabilities.setCapability(IOSMobileCapabilityType.SCREENSHOT_WAIT_TIMEOUT, 60000);
        //capabilities.setCapability("webkitResponseTimeout", 60000);
        //capabilities.setCapability(IOSMobileCapabilityType.IOS_INSTALL_PAUSE, 20000);
        //capabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 30000);
        //capabilities.setCapability(IOSMobileCapabilityType.WDA_CONNECTION_TIMEOUT, 30000);

        //capabilities.setCapability(IOSMobileCapabilityType.USE_PREBUILT_WDA, Configuration.USE_PREBUILT_WDA);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, iOSVersion);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        if (IOS_SIMULATOR_UUID == null || IOS_SIMULATOR_UUID.isEmpty()) {
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        } else {
            capabilities.setCapability(MobileCapabilityType.UDID, IOS_SIMULATOR_UUID);
        }
        //capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, simulatorPort);
        //capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, false);
        //capabilities.setCapability(IOSMobileCapabilityType.AUTO_DISMISS_ALERTS, false);
        capabilities.setCapability(MobileCapabilityType.APP, Configuration.IOS_APP_PATH);
        //capabilities.setCapability(IOSMobileCapabilityType.APP_NAME, "Pocapp");
        //capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.denysord1988.pocapp");
        //capabilities.setCapability(IOSMobileCapabilityType.ALLOW_TOUCHID_ENROLL, true);
        return capabilities;
    }
}
