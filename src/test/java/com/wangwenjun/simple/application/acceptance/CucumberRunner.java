package com.wangwenjun.simple.application.acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty", "json:target/cucumber-report/cucumber-report.json",
                "junit:target/cucumber-junit.xml",
                "html:target/site/cucumber-pretty.html"
        },
        features = {
                "classpath:com/wangwenjun/simple/application/acceptance"
        },
        glue = {
                "com.wangwenjun.simple.application.acceptance"
        },
        dryRun = false,
        tags = "@acceptance"
)
public class CucumberRunner {

}
