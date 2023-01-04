package com.denysord88.appium;

import com.denysord88.conf.Configuration;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.HashMap;

import static com.denysord88.conf.Configuration.*;

public class AppiumInstance {
    public static HashMap<String, AppiumInstance> devices = new HashMap<>();
    public MobilePlatform platform;
    public WebDriverWait wait;
    protected AppiumDriverLocalService service;
    protected AppiumDriver driver;
    protected String UDID;

    public AppiumDriver getDriver() {
        return driver;
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
                    //"| grep '(Booted)' " +
                    "| cut -d \"(\" -f2 " +
                    "| cut -d \")\" -f1" +
                    "); do /usr/bin/xcrun simctl delete $udid; done"};
            Runtime.getRuntime().exec(deleteAllAppiumSimulators);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected DesiredCapabilities prepareDriverCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // Logging
        capabilities.setCapability(MobileCapabilityType.ENABLE_PERFORMANCE_LOGGING, ENABLE_LOGGING);
        //capabilities.setCapability("skipLogCapture", !ENABLE_LOGGING);
        //capabilities.setCapability("skipLogcatCapture", !ENABLE_LOGGING);
        //capabilities.setCapability("appium:retryBackoffTime", 1000);
        capabilities.setCapability("appium:noReset", false);

        // Timeouts
        int defSec = 1;
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60000);
        //capabilities.setCapability("appWaitDuration", 60000);
        //capabilities.setCapability("deviceReadyTimeout", 60000);

        //capabilities.setCapability("appium:retryBackoffTime", 60000);
        //capabilities.setCapability("appium:keepAliveTimeout", 60000);

        //capabilities.setCapability(MobileCapabilityType.LANGUAGE, LANGUAGE);
        //capabilities.setCapability(MobileCapabilityType.LOCALE, LOCALE);

        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Flutter");
        //capabilities.setCapability("appium-version", "2.0.0-beta.46");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        return capabilities;
    }
}
