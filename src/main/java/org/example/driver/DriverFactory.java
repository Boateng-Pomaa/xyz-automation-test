package org.example.driver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {

    private static final String BUNDLED_DRIVER_PATH =
            "resources/chromedriver-win64/chromedriver-win64/chromedriver.exe";

    private DriverFactory() {
    }

    public static WebDriver createChromeDriver() {
        ChromeOptions options = buildOptions();
        try {
            return new ChromeDriver(options);
        } catch (WebDriverException e) {
            Path bundledDriver = Paths.get(System.getProperty("user.dir"), BUNDLED_DRIVER_PATH);
            if (!Files.exists(bundledDriver)) {
                throw e;
            }
            System.setProperty("webdriver.chrome.driver", bundledDriver.toString());
            return new ChromeDriver(options);
        }
    }

    private static ChromeOptions buildOptions() {
        ChromeOptions options = new ChromeOptions();
        // The target app sits behind a bot-check interstitial that never clears once it
        // detects navigator.webdriver / the ChromeDriver automation banner - these flags
        // suppress both so the real Angular app loads.
        options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");
        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }
        return options;
    }
}
