package com.denysord88.appium;

import com.denysord88.conf.Configuration;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.denysord88.appium.AppiumController.runAppiumService;
import static com.denysord88.conf.Configuration.*;

public class AppiumAndroidInstance extends AppiumInstance {

    public AppiumAndroidInstance(String wdaPort, String simulatorPort, String OSVersion, String deviceName) {
        try {
            String[] killAllAppiumServices = {"/bin/zsh", "-c", "avdmanager create avd --name \"TA_" + deviceName + "\" --device \"" + deviceName + "\" -k \"system-images;android-33;google_apis_playstore;arm64-v8a\" && " +
                    "nohup emulator -avd TA_" + deviceName + " >/dev/null 2>&1 &"};
            Runtime.getRuntime().exec(killAllAppiumServices);
        } catch (IOException e) {
            e.printStackTrace();
        }
        service = runAppiumService(wdaPort);
        DesiredCapabilities capabilities = prepareAndroidDriverCapabilities(simulatorPort, OSVersion, deviceName);
        driver = new AndroidDriver(service.getUrl(), capabilities);
        driver.executeScript("flutter:waitForFirstFrame");
        wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICITLY_WAIT_SECONDS));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        //UDID = driver.getCapabilities().getCapability("udid").toString();
    }

    public AndroidDriver getDriver() {
        return (AndroidDriver) driver;
    }

    public void teardown() {
        /*if (driver != null) {
            driver.quit();
        }*/
        if (service != null) {
            service.stop();
        }

        /*try {
            Runtime.getRuntime().exec("/usr/bin/xcrun simctl delete " + UDID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }

    private DesiredCapabilities prepareAndroidDriverCapabilities(String simulatorPort, String OSVersion, String deviceName) {
        DesiredCapabilities capabilities = super.prepareDriverCapabilities();

        // Timeouts
        int defSec = 1;
        /*capabilities.setCapability(AndroidMobileCapabilityType.ADB_EXEC_TIMEOUT, defSec);
        capabilities.setCapability(AndroidMobileCapabilityType.ADB_PORT, simulatorPort);
        capabilities.setCapability(AndroidMobileCapabilityType.ANDROID_INSTALL_PATH, );
        capabilities.setCapability(AndroidMobileCapabilityType.ANDROID_SCREENSHOT_PATH, );
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, );
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, );
        capabilities.setCapability(AndroidMobileCapabilityType.AVD, );
        capabilities.setCapability(AndroidMobileCapabilityType.TIMEOUTS, );*/

        capabilities.setCapability(AndroidMobileCapabilityType.ANDROID_DEVICE_READY_TIMEOUT, 120000);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION, 120000);
        capabilities.setCapability(AndroidMobileCapabilityType.ADB_EXEC_TIMEOUT, 120000);
        capabilities.setCapability(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT, 120000);
        capabilities.setCapability(AndroidMobileCapabilityType.AVD_READY_TIMEOUT, 120000);
        capabilities.setCapability(AndroidMobileCapabilityType.DEVICE_READY_TIMEOUT, 120000);
        capabilities.setCapability(AndroidMobileCapabilityType.ANDROID_INSTALL_TIMEOUT, 120000);
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_WEBVIEW_TIMEOUT, 120000);

        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, OSVersion);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "TA_" + deviceName);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Flutter");
        capabilities.setCapability(MobileCapabilityType.APP, Configuration.ANDROID_APP_PATH);
        return capabilities;
    }

    public static void deleteAllAndroidEmulators() {
        try {
            for(int i = 0; i < ANDROID_DEVICES.length; i++) {
                String[] shutdownAllEmulators = {"/bin/zsh", "-c", "adb -e emu kill"};
                Runtime.getRuntime().exec(shutdownAllEmulators);
                String[] killEmulator = {"/bin/zsh", "-c", "avdmanager delete avd --name \"TA_" + ANDROID_DEVICES[i] + "\""};
                Runtime.getRuntime().exec(killEmulator);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
