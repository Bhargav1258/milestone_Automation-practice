package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.WriteExcelwithdp;

public class TestListener implements ITestListener {

    WriteExcelwithdp excel = new WriteExcelwithdp();

    @Override
    public void onStart(ITestContext context) {
        excel.createExcelFile(); // Ensures Excel framework tabs exist before execution
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        routeToExcelSuite(result, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        routeToExcelSuite(result, "FAILED");
    }

    private void routeToExcelSuite(ITestResult result, String status) {
        String testName = result.getMethod().getMethodName();
        String[] groups = result.getMethod().getGroups();
        
        boolean isSmoke = false;
        boolean isRegression = false;

        for (String group : groups) {
            if (group.equalsIgnoreCase("smoke")) isSmoke = true;
            if (group.equalsIgnoreCase("regression")) isRegression = true;
        }

        // Dynamically log into corresponding suite tabs based on test tags
        if (isSmoke) {
            excel.logSuiteResult("SmokeSuite", testName, status);
        }
        if (isRegression) {
            excel.logSuiteResult("RegressionSuite", testName, status);
        }
    }
}