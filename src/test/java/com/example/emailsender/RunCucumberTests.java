package com.example.emailsender;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/example/emailsender/features",
        glue = "com.example.emailsender.steps",
        plugin = {"html:target/cucumber-reports.html"},
        monochrome = true,
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunCucumberTests {
}
