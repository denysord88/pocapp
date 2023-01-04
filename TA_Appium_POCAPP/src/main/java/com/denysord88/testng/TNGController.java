package com.denysord88.testng;

import com.denysord88.Main;
import com.denysord88.conf.Configuration;
import io.appium.java_client.remote.MobilePlatform;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.denysord88.conf.Configuration.*;

public class TNGController {
    public static XmlSuite createTestNGSuite(String name) {
        XmlSuite suite = new XmlSuite();
        suite.setName(name);
        suite.setConfigFailurePolicy(XmlSuite.FailurePolicy.CONTINUE);
        suite.setParallel(XmlSuite.ParallelMode.TESTS);
        suite.setThreadCount(TESTING_THREADS);
        return suite;
    }

    public static void generateTests(XmlSuite suite) {
        int simulatorPort = getFreePortFrom(4000);
        System.out.println("[TA_FE] (" + Main.stopwatch + ") Simulator port " + simulatorPort);
        int wdaPort = getFreePortFrom(4200);
        System.out.println("[TA_FE] (" + Main.stopwatch + ") WDA Port " + wdaPort);

        if (IOS_VERSIONS.length > 0 && !IOS_VERSIONS[0].isEmpty()) {
            for (int iosVersionNum = 0; iosVersionNum < IOS_VERSIONS.length; iosVersionNum++) {
                String iOSVersion = IOS_VERSIONS[iosVersionNum];
                for (int simulatorNum = 0; simulatorNum < IOS_DEVICES.length; simulatorNum++) {
                    String simulatorName = IOS_DEVICES[simulatorNum];
                    System.out.println("[TA_FE] (" + Main.stopwatch + ") Generating tests for '" + simulatorName +
                            "' with OS version '" + iOSVersion + "'");
                    XmlTest test = new XmlTest(suite);
                    test.setName("Test on " + MobilePlatform.IOS + " v" + iOSVersion + "; " + "Device name: " + simulatorName + ";");
                    test.addParameter("simulatorPort", "" + (simulatorPort));
                    simulatorPort = getFreePortFrom(++simulatorPort);
                    test.addParameter("wdaPort", "" + wdaPort);
                    wdaPort = getFreePortFrom(++wdaPort);
                    if (simulatorPort == wdaPort) wdaPort = getFreePortFrom(++wdaPort);
                    test.addParameter("OSVersion", iOSVersion);
                    test.addParameter("deviceName", simulatorName);
                    test.addParameter("configfailurepolicy", "continue");
                    test.addParameter("platform", MobilePlatform.IOS);

                    List<XmlPackage> packages = new LinkedList<>();
                    packages.add(new XmlPackage("tests.*"));
                    packages.add(new XmlPackage("tests.*.*"));
                    test.setXmlPackages(packages);

                    test.setExcludedGroups(Arrays.asList(EXCLUDED_GROUPS));
                    System.out.println("[TA_FE] (" + Main.stopwatch + ") Tests for '" + simulatorName +
                            "' with OS version '" + iOSVersion + "' generated");
                }
            }
        }

        if (ANDROID_VERSIONS.length > 0 && !ANDROID_VERSIONS[0].isEmpty()) {
            for (int androidVersionNum = 0; androidVersionNum < ANDROID_VERSIONS.length; androidVersionNum++) {
                String androidVersion = ANDROID_VERSIONS[androidVersionNum];
                for (int emulatorNum = 0; emulatorNum < ANDROID_DEVICES.length; emulatorNum++) {
                    String emulatorName = ANDROID_DEVICES[emulatorNum];
                    XmlTest test = new XmlTest(suite);
                    test.setName("Test on " + MobilePlatform.ANDROID + " v" + androidVersion + "; " + "Device name: " + emulatorName + ";");
                    test.addParameter("simulatorPort", "" + (simulatorPort));
                    simulatorPort = getFreePortFrom(++simulatorPort);
                    test.addParameter("wdaPort", "" + wdaPort);
                    wdaPort = getFreePortFrom(++wdaPort);
                    if (simulatorPort == wdaPort) wdaPort = getFreePortFrom(++wdaPort);
                    test.addParameter("OSVersion", androidVersion);
                    test.addParameter("deviceName", emulatorName);
                    test.addParameter("configfailurepolicy", "continue");
                    test.addParameter("platform", MobilePlatform.ANDROID);

                    List<XmlPackage> packages = new LinkedList<>();
                    packages.add(new XmlPackage("tests.*"));
                    packages.add(new XmlPackage("tests.*.*"));
                    test.setXmlPackages(packages);

                    test.setExcludedGroups(Arrays.asList(EXCLUDED_GROUPS));
                }
            }
        }
    }

    public static TestNG generateTestNGRunner(XmlSuite suite) {
        TestNG runner = new TestNG();
        runner.setUseDefaultListeners(false);
        TestListenerAdapter tla = new TestListenerAdapter();
        runner.addListener(tla);
        runner.addListener(new TestEventsListener());

        List<XmlSuite> suiteFiles = new ArrayList<>();
        suiteFiles.add(suite);
        runner.setXmlSuites(suiteFiles);
        return runner;
    }

    private static int getFreePortFrom(int startingPort) {
        while (startingPort <= 5000) {
            try (Socket ignored = new Socket("localhost", startingPort)) {
            } catch (IOException e) {
                return startingPort;
            }
            startingPort++;
        }
        throw new RuntimeException("All HTTP ports 4000 - 5000 are busy!");
    }
}
