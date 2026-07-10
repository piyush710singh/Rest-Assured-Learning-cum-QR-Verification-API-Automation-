package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

public final class ExtentReportManager implements ITestListener {

    private static final String REPORT_PATH = Paths.get(
            System.getProperty("user.dir"),
            "test-output",
            "ExtentReport.html"
    ).toString();

    private static final ExtentReports EXTENT_REPORTS = new ExtentReports();
    private static final ThreadLocal<ExtentTest> CURRENT_TEST = new ThreadLocal<>();

    public ExtentReportManager() {
    }

    static {
        ensureReportDirectory();

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_PATH);
        sparkReporter.config().setDocumentTitle("API Automation Report");
        sparkReporter.config().setReportName("RestAssuredLearning Test Results");
        sparkReporter.config().setEncoding("UTF-8");
        sparkReporter.config().setTimeStampFormat("dd-MMM-yyyy hh:mm:ss a");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimelineEnabled(true);
        sparkReporter.viewConfigurer().viewOrder().as(
                EnumSet.of(
                        ViewName.DASHBOARD,
                        ViewName.TEST,
                        ViewName.CATEGORY,
                        ViewName.AUTHOR,
                        ViewName.DEVICE,
                        ViewName.EXCEPTION,
                        ViewName.LOG
                ).toArray(new ViewName[0])
        );

        EXTENT_REPORTS.attachReporter(sparkReporter);
        EXTENT_REPORTS.setSystemInfo("Application", "Pest Store Users API");
        EXTENT_REPORTS.setSystemInfo("Operating System", System.getProperty("os.name"));
        EXTENT_REPORTS.setSystemInfo("User Name", System.getProperty("user.name"));
        EXTENT_REPORTS.setSystemInfo("Environment", "QA");
        EXTENT_REPORTS.setSystemInfo("User", "pavan");
        EXTENT_REPORTS.setSystemInfo("Project Name", "RestAssuredLearning");
        EXTENT_REPORTS.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    private static void ensureReportDirectory() {
        Path reportDirectory = Paths.get(REPORT_PATH).getParent();
        if (reportDirectory == null) {
            return;
        }
        try {
            Files.createDirectories(reportDirectory);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create ExtentReports output directory: " + reportDirectory, e);
        }
    }

    public static ExtentReports getExtentReports() {
        return EXTENT_REPORTS;
    }

    public static ExtentTest createTest(String testName) {
        return EXTENT_REPORTS.createTest(testName);
    }

    public static ExtentTest createTest(String testName, String description) {
        return EXTENT_REPORTS.createTest(testName, description);
    }

    public static ExtentTest createTest(ITestResult result) {
        ExtentTest test = EXTENT_REPORTS.createTest(result.getName());
        CURRENT_TEST.set(test);
        return test;
    }

    public static ExtentTest getCurrentTest() {
        return CURRENT_TEST.get();
    }

    public static void markTestStart(ITestResult result) {
        createTest(result);
    }

    public static void markTestSuccess(ITestResult result) {
        ExtentTest test = getOrCreateTest(result);
        assignCategories(test, result);
        test.log(Status.PASS, "Test Passed");
    }

    public static void markTestFailure(ITestResult result) {
        ExtentTest test = getOrCreateTest(result);
        assignCategories(test, result);
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            test.log(Status.FAIL, throwable);
        } else {
            test.log(Status.FAIL, "Test Failed");
        }
    }

    public static void markTestSkipped(ITestResult result) {
        ExtentTest test = getOrCreateTest(result);
        assignCategories(test, result);
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            test.log(Status.SKIP, throwable);
        } else {
            test.log(Status.SKIP, "Test Skipped");
        }
    }

    public static void finishReport() {
        flush();
        CURRENT_TEST.remove();
    }

    public static void flush() {
        EXTENT_REPORTS.flush();
    }

    private static ExtentTest getOrCreateTest(ITestResult result) {
        ExtentTest test = CURRENT_TEST.get();
        if (test == null) {
            test = createTest(result);
        }
        return test;
    }

    private static void assignCategories(ExtentTest test, ITestResult result) {
        String[] groups = result.getMethod().getGroups();
        if (groups != null && groups.length > 0) {
            test.assignCategory(groups);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        markTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        markTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        markTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        markTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        markTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {
        // No-op.
    }

    @Override
    public void onFinish(ITestContext context) {
        finishReport();
    }
}
