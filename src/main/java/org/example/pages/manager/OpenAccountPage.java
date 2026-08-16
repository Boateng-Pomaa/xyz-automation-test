package org.example.pages.manager;

import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class OpenAccountPage extends BasePage {

    private static final By CUSTOMER_SELECT = By.id("userSelect");
    private static final By CURRENCY_SELECT = By.id("currency");
    private static final By SUBMIT_BUTTON =
            By.cssSelector("form[ng-submit='process()'] button[type='submit']");

    public OpenAccountPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects the customer (by "First Last" visible text) and currency, submits,
     * and returns the resulting native alert's text.
     */
    public String openAccount(String customerFullName, String currency) {
        new Select(waitVisible(CUSTOMER_SELECT)).selectByVisibleText(customerFullName);
        new Select(waitVisible(CURRENCY_SELECT)).selectByVisibleText(currency);
        click(SUBMIT_BUTTON);
        return acceptAlertAndGetText();
    }
}
