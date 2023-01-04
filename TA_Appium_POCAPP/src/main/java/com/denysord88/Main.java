package com.denysord88;

import com.google.common.base.Stopwatch;
import io.appium.java_client.remote.MobilePlatform;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.*;

import static com.denysord88.conf.Configuration.*;
import static com.denysord88.testng.TNGController.*;
import static tests.BaseTest.checkAndFixScreenshotsPath;

public class Main {
    public static Stopwatch stopwatch = Stopwatch.createStarted();
    public static final String SCREENSHOTS_FULL_PATH = SCREENSHOTS_PATH + "/" + new Date() + "/";

    public static void main(String[] args) {
        System.out.println("[TA_FE] Checking the screenshots path");
        checkAndFixScreenshotsPath();
        System.out.println("[TA_FE] Screenshots path verified and fixed");

        // createTestNGSuite
        System.out.println("[TA_FE] Creating TestNG XML suite");
        XmlSuite suite = createTestNGSuite("TestNG XML created from Java Code");
        System.out.println("[TA_FE] TestNG XML suite created");

        // generateTests
        System.out.println("[TA_FE] Generating tests");
        generateTests(suite);
        System.out.println("[TA_FE] Tests generated");

        // generateTestNGRunner
        System.out.println("[TA_FE] Generating TestNG Runner");
        TestNG runner = generateTestNGRunner(suite);
        System.out.println("[TA_FE] TestNG Runner generated");

        System.out.println("[TA_FE] Running tests");
        runner.run();
    }
}