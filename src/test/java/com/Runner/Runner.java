package com.Runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "E:\\My Projects\\All Projects\\API Testing\\src\\main\\resources\\features",
        glue = {"stepDefinition"},
       // tags = "@Testcase or @Testtwo or @New",
        plugin={"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                },
        tags="@TestThree",
        stepNotifications= true,
        monochrome=true
        //dryRun = true
        //  "json:target/cucumber-reports/cucumber.json"
        //"html:target/cucumber-reports.html"
)

public class Runner {

}
