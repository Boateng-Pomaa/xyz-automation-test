package org.example.pages;

import org.example.pages.customer.CustomerLoginPage;
import org.example.pages.manager.ManagerDashboardPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public static final String URL =
            "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";

    private static final By CUSTOMER_LOGIN_BUTTON = By.cssSelector("[ng-click='customer()']");
    private static final By MANAGER_LOGIN_BUTTON = By.cssSelector("[ng-click='manager()']");
    private static final By HOME_BUTTON = By.cssSelector("[ng-click='home()']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(URL);
        waitVisible(CUSTOMER_LOGIN_BUTTON);
        return this;
    }

    /**
     * Returns to the home screen for a new test without forcing a fresh browser
     * navigation: the app's bot-check interstitial can re-trigger (or the origin
     * can rate-limit) under repeated full page loads, so once the browser has
     * loaded the app for the first time, later resets go through the in-app
     * "Home" link instead of driver.get(URL).
     */
    public HomePage reset() {
        if ("data:,".equals(driver.getCurrentUrl())) {
            return open();
        }
        click(HOME_BUTTON);
        waitVisible(CUSTOMER_LOGIN_BUTTON);
        return this;
    }

    public CustomerLoginPage goToCustomerLogin() {
        click(CUSTOMER_LOGIN_BUTTON);
        return new CustomerLoginPage(driver);
    }

    public ManagerDashboardPage goToManagerLogin() {
        click(MANAGER_LOGIN_BUTTON);
        return new ManagerDashboardPage(driver);
    }
}
