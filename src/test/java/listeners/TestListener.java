package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.WriteExcelwithdp;

public class TestListener implements ITestListener {

    WriteExcelwithdp excel = new WriteExcelwithdp();

    @Override
    public void onStart(ITestContext context) {

        System.out.println("\n");
        System.out.println("====================================================");
        System.out.println("          AUTOMATION EXECUTION STARTED");
        System.out.println("          SUITE : " + context.getName());
        System.out.println("====================================================");
        System.out.println("\n");

        excel.createExcelFile();
    }

    @Override
    public void onTestStart(ITestResult result) {

        System.out.println("\n");
        System.out.println("----------------------------------------------------");
        System.out.println("STARTING TEST : "
                + result.getMethod().getMethodName());
        System.out.println("----------------------------------------------------");
        System.out.println("\n");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        System.out.println("====================================================");
        System.out.println("TEST CASE : " + testName);
        System.out.println("STATUS    : PASSED");
        System.out.println("====================================================");

        routeToExcelSuite(result, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        System.out.println("====================================================");
        System.out.println("TEST CASE : " + testName);
        System.out.println("STATUS    : FAILED");
        System.out.println("REASON    : "
                + result.getThrowable());
        System.out.println("====================================================");

        routeToExcelSuite(result, "FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        System.out.println("====================================================");
        System.out.println("TEST CASE : " + testName);
        System.out.println("STATUS    : SKIPPED");
        System.out.println("====================================================");
    }

    @Override
    public void onFinish(ITestContext context) {

        System.out.println("\n");
        System.out.println("====================================================");
        System.out.println("          AUTOMATION EXECUTION COMPLETED");
        System.out.println("====================================================");
        System.out.println("\n");
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

            if (group.equalsIgnoreCase("smoke")) {
                isSmoke = true;
            }

            if (group.equalsIgnoreCase("regression")) {
                isRegression = true;
            }
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