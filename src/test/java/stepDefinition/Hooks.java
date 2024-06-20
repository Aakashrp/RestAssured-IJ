//package stepDefinition;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.configuration.Theme;
//import io.cucumber.java.Scenario;
//import org.junit.After;
//import org.junit.Before;
//
//public class Hooks {
//    private static ExtentReports extent;
//    public static ExtentTest test;
//
//
//    @Before
//    public void setUp(Scenario scenario) {
//        if (extent == null) {
//            ExtentSparkReporter spark = new ExtentSparkReporter("Spark.html");
//            spark.config().setTheme(Theme.DARK);
//            spark.config().setDocumentTitle("MyReport");
//            extent = new ExtentReports();
//            extent.attachReporter(spark);
//        }
//        test = extent.createTest("Scenario: " + scenario.getName());
//    }
//
//    @After
//    public void tearDown(Scenario scenario) {
//        if (scenario.isFailed()) {
//            test.fail("Scenario failed");
//        } else {
//            test.pass("Scenario passed");
//        }
//        extent.flush();
//    }
//}