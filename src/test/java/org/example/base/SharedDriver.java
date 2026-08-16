package org.example.base;

import org.example.driver.DriverFactory;
import org.openqa.selenium.WebDriver;

/**
 * One Chrome session for the entire test run, reused across every test class.
 *
 * The target app sits behind a bot-check interstitial that can escalate to
 * outright rate-limiting under repeated full page loads, so tests must not
 * each launch their own browser/navigation - see {@link org.example.pages.HomePage#reset()}.
 */
final class SharedDriver {

    private static volatile WebDriver instance;

    private SharedDriver() {
    }

    static synchronized WebDriver get() {
        if (instance == null) {
            instance = DriverFactory.createChromeDriver();
            Runtime.getRuntime().addShutdownHook(new Thread(instance::quit));
        }
        return instance;
    }
}
