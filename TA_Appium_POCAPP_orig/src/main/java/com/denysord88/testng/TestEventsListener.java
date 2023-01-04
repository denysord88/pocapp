package com.denysord88.testng;

import com.google.common.base.Stopwatch;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;

import static com.denysord88.Main.SCREENSHOTS_FULL_PATH;
import static com.denysord88.appium.AppiumAndroidInstance.deleteAllAndroidEmulators;
import static com.denysord88.appium.AppiumInstance.devices;
import static com.denysord88.appium.AppiumiOSInstance.deleteAlliOSSimulators;

public class TestEventsListener implements ITestListener {
    public Stopwatch stopwatch;

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        new File(SCREENSHOTS_FULL_PATH + "testFailures/").mkdir();
        StringBuilder params = new StringBuilder("testFailures/Params_");
        for (int i = 0; i < iTestResult.getParameters().length; i++) {
            params.append(iTestResult.getParameters()[i]);
            params.append("_");
        }
        params.append("Failed_method_");
        params.append(iTestResult.getTestClass().getName() + "." + iTestResult.getMethod().getMethodName());
        params.append("().png");
        takeScreenshot(getiOSDriver(), params.toString());

        deleteAlliOSSimulators();
        deleteAllAndroidEmulators();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {
        stopwatch = Stopwatch.createStarted();
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        if (iTestContext.getFailedConfigurations().getAllResults().size() > 0) {
            deleteAlliOSSimulators();
            deleteAllAndroidEmulators();
            System.err.println("There are Configuration Failures:");
            iTestContext.getFailedConfigurations().getAllResults().forEach(iTestResult -> {
                System.err.println("OS - " + iTestContext.getCurrentXmlTest().getParameter("OSVersion"));
                System.err.println("Device - " + iTestContext.getCurrentXmlTest().getParameter("deviceName"));
                System.err.println("Class name - " + iTestResult.getTestClass().getName());
                System.err.println("Method name - " + iTestResult.getMethod().getMethodName());
                System.err.println("Message - " + iTestResult.getThrowable().getMessage());
            });
            Runtime.getRuntime().halt(1);
        }
        if (iTestContext.getFailedTests().size() > 0) {
            StringBuilder sb = new StringBuilder();
            prepareReportHeader(sb, iTestContext);
            addTestFailuresToTestReport(sb, iTestContext);
            System.err.println(sb.toString());
            Runtime.getRuntime().halt(1);
        }
        deleteAlliOSSimulators();
        deleteAllAndroidEmulators();
    }

    private void prepareReportHeader(StringBuilder reportString, ITestContext testCases) {
        reportString.append("\n\n===============================================\n");
        reportString.append(testCases.getSuite().getName());
        reportString.append("\nTotal tests run: ");
        reportString.append(testCases.getAllTestMethods().length);
        reportString.append(", Passes: ");
        reportString.append(testCases.getPassedTests().size());
        reportString.append(", Failures: ");
        reportString.append(testCases.getFailedTests().size());
        reportString.append(", Skips: ");
        reportString.append(testCases.getSkippedTests().size());
        reportString.append("\n===============================================\n\n");
    }

    private void addTestFailuresToTestReport(StringBuilder reportString, ITestContext testCases) {
        testCases.getFailedTests().getAllResults().forEach(iTestResult -> {
            reportString.append(">>> Test method failed: ")
                    .append(iTestResult.getTestClass().getName())
                    .append(".")
                    .append(iTestResult.getName())
                    .append("()")
                    .append("\niOS - ")
                    .append(testCases.getCurrentXmlTest().getParameter("OSVersion"))
                    .append("\nDevice - ")
                    .append(testCases.getCurrentXmlTest().getParameter("deviceName"))
                    .append("\nFailed cause: ")
                    .append(iTestResult.getThrowable().getMessage()
                    );
            reportString.append("\n===========================================\n");
        });
    }

    public static void takeScreenshot(IOSDriver driver, String fileName) {
        File f = new File(SCREENSHOTS_FULL_PATH + fileName);
        if (f.exists()) f.delete();
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(SCREENSHOTS_FULL_PATH + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static IOSDriver getiOSDriver() {
        String iOSVersion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("OSVersion");
        String deviceName = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("deviceName");
        return (IOSDriver) devices.get(iOSVersion + deviceName).getDriver();
    }

    public static AndroidDriver getAndroidDriver() {
        String OSVersion = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("OSVersion");
        String deviceName = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("deviceName");
        return (AndroidDriver) devices.get(OSVersion + deviceName).getDriver();
    }
}
