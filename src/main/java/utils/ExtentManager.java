package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getReport() {

        if(extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(
                    "test-output/ExtentReport.html");

            spark.config().setReportName(
                    "Automation Practice Report");

            spark.config().setDocumentTitle(
                    "Selenium Automation Report");

            extent = new ExtentReports();

            extent.attachReporter(spark);

            extent.setSystemInfo(
                    "Tester",
                    "Bhargav");

            extent.setSystemInfo(
                    "Framework",
                    "Selenium TestNG");
        }

        return extent;
    }
}