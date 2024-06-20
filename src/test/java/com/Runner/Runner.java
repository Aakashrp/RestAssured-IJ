package com.Runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "E:\\My Projects\\All Projects\\API Testing\\src\\main\\resources\\features",
        glue = "stepDefinition",
       // tags = "@Testcase or @Testtwo or @New",
)

public class Runner {

}
