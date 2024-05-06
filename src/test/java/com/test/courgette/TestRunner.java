package com.test.courgette;

import courgette.api.CourgetteOptions;
import courgette.api.CucumberOptions;
import org.junit.runner.RunWith;
import courgette.api.CourgetteTestOutput;
import courgette.api.CourgetteRunLevel;
import courgette.api.junit.Courgette;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;


@RunWith(Courgette.class)
@CourgetteOptions(
    threads = 1,
    runLevel = CourgetteRunLevel.SCENARIO,
    testOutput = CourgetteTestOutput.CONSOLE,
    rerunFailedScenarios = true,
    rerunAttempts = 1,
    excludeFeatureFromRerun = "",
    excludeTagFromRerun = "",
    reportTitle = "Courgette test execution",
    reportTargetDir = "build/reports/html/chartReport",
    environmentInfo = "",
    cucumberOptions =
    @CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.test.courgette.steps",
        tags = "@test",
        plugin = {
            "pretty",
            "json:build/reports/json/cucumber.json",
            "html:build/reports/html/cucumber.html",
            "junit:build/reports/cucumber.xml"
        }))
public class TestRunner {

  public static void main(String[] args) throws InitializationError {
    System.setProperty("cucumber.features", "classpath:features/");
    /*
    It would be good to have a courgette built-in capability to read values directly from ENV variables
    and an "io.cucumber.core.cli.Main.run()" like feature.
    */
    setCourgetteProperty("courgette.threads", "THREADS");
    setCourgetteProperty("courgette.runLevel", "RUN_LEVEL");
    setCourgetteProperty("cucumber.tags", "TAGS");
    setCourgetteProperty("courgette.environmentInfo", "ENV_INFO");
    setCourgetteProperty("courgette.rerunAttempts", "RERUN_ATTEMPTS");
    setCourgetteProperty("courgette.excludeTagFromRerun", "TAG_EXCLUSION");
    setCourgetteProperty("courgette.rerunFailedScenarios", "RERUN_FAILED_SCENARIOS");
    setCourgetteProperty("courgette.excludeFeatureFromRerun", "EXCLUDE_FEATURE_FROM_RERUN");
    new Courgette(TestRunner.class).run(new RunNotifier());
    System.out.println("\n+++++++++++++++++++++++++\n");
    System.out.println("\n++++EXECUTION FINISHED+++\n");
    System.out.println("\n+++++++++++++++++++++++++\n");
  }

  public static void setCourgetteProperty(String courgetteProperty, String envVariable) {
    String envValue = System.getenv().getOrDefault(envVariable, null);
    if (envValue != null)
      System.setProperty(courgetteProperty, envValue);
  }
}