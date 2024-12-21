package com.Listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.utils.Util;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.Map;

public class ExtentTestNGListener implements ITestListener {
    private ExtentReports extent;
    private ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        sparkReporter.config().setReportName("Automation Test Results");
        sparkReporter.config().setDocumentTitle("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Tester", "Junaid");
        extent.setSystemInfo("Environment", "QA");
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test Passed");
        logActualAndExpectedValues(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail("Test Failed");
        if (result.getThrowable() != null) {
            test.fail(result.getThrowable());
        }
        logActualAndExpectedValues(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private void logActualAndExpectedValues(ITestResult result) {
        // Check if parameters exist and fetch the first parameter
        if (result.getParameters() == null || result.getParameters().length == 0) {
            test.warning("No parameters passed to the test method.");
            return;
        }

        String input = (String) result.getParameters()[0]; // Assuming input is passed as the first parameter

        if (input == null || input.isEmpty()) {
            test.warning("Test input is null or empty.");
            return;
        }

        try {
            // Fetch actual and expected values using the utility class
            Map<String, Object> values = Util.getActualAndExpectedValues(input);

            // Handle null or missing values
            Object actual = values.get("actual");
            Object expected = values.get("expected");

            if (actual == null) {
                test.warning("Actual values are null.");
            } else {
                test.info("**Actual:** " + actual);
            }

            if (expected == null) {
                test.warning("Expected values are null.");
            } else {
                test.info("**Expected:** " + expected);
            }

            test.info("**Input:** " + input);

        } catch (IOException e) {
            test.fail("Error fetching actual and expected values: " + e.getMessage());
        }
    }
}
