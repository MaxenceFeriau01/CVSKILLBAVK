package com.ensemble.entreprendre.features;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", publish = false, plugin = { "pretty",
        "html:target/cucumber.html" })

public class CucumberFeatureTest {
}
