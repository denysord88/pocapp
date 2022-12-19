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
        checkAndFixScreenshotsPath();

        // createTestNGSuite
        XmlSuite suite = createTestNGSuite("TestNG XML created from Java Code");

        // generateTests
        generateTests(suite);

        // generateTestNGRunner
        TestNG runner = generateTestNGRunner(suite);

        runner.run();
    }
}