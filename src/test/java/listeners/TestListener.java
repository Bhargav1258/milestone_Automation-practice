package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.ExtentManager;
import utils.WriteExcelwithdp;

public class TestListener implements ITestListener {

    WriteExcelwithdp excel = new WriteExcelwithdp();

    private ExtentReports extent =
            ExtentManager.getReport();

    private ExtentTest test;

    @Override
    public void onStart(ITestContext context) {

        System.out.println("\n====================================");
        System.out.println(" AUTOMATION EXECUTION STARTED");
        System.out.println("====================================\n");

        excel.createExcelFile();
    }

    @Override
    public void onTestStart(ITestResult result) {

        test = extent.createTest(
                result.getMethod().getMethodName());

        System.out.println("\n====================================================");
        System.out.println("TEST CASE : "
                + result.getMethod().getMethodName());
        System.out.println("STATUS    : RUNNING");
        System.out.println("====================================================");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.pass("Test Passed");

        System.out.println("====================================================");
        System.out.println("TEST CASE : "
                + result.getMethod().getMethodName());
        System.out.println("STATUS    : PASSED");
        System.out.println("====================================================");

        routeToExcelSuite(result, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.fail(result.getThrowable());

        System.out.println("====================================================");
        System.out.println("TEST CASE : "
                + result.getMethod().getMethodName());
        System.out.println("STATUS    : FAILED");
        System.out.println("REASON    : "
                + result.getThrowable());
        System.out.println("====================================================");

        routeToExcelSuite(result, "FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.skip("Test Skipped");

        System.out.println("====================================================");
        System.out.println("TEST CASE : "
                + result.getMethod().getMethodName());
        System.out.println("STATUS    : SKIPPED");
        System.out.println("====================================================");
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();

        System.out.println("\n====================================================");
        System.out.println(" AUTOMATION EXECUTION COMPLETED");
        System.out.println("====================================================");

        System.out.println("\n📊 Extent Report Generated:");
        System.out.println("test-output\\ExtentReport.html");
    }

    private void routeToExcelSuite(
            ITestResult result,
            String status) {

        String testName =
                result.getMethod().getMethodName();

        String[] groups =
                result.getMethod().getGroups();

        boolean isSmoke = false;
        boolean isRegression = false;

        for (String group : groups) {

            if (group.equalsIgnoreCase("smoke"))
                isSmoke = true;

            if (group.equalsIgnoreCase("regression"))
                isRegression = true;
        }

        if (isSmoke) {

            excel.logSuiteResult(
                    "SmokeSuite",
                    testName,
                    status);
        }

        if (isRegression) {

            excel.logSuiteResult(
                    "RegressionSuite",
                    testName,
                    status);
        }
    }
}