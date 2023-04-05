package com.bdd.demo.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:build/cucumber/report.html"},
        features = "classpath:features",
        glue = {"com.bdd.demo"}
)
public class CucumberRunnerTests {
}
