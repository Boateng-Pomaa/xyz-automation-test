package org.example.pages.customer;

import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DepositPage extends BasePage {

    private static final By AMOUNT_INPUT = By.cssSelector("input[ng-model='amount']");
    private static final By SUBMIT_BUTTON =
            By.cssSelector("form[ng-submit='deposit()'] button[type='submit']");
    private static final By MESSAGE = By.cssSelector("span.error");

    public DepositPage(WebDriver driver) {
        super(driver);
    }

    public String deposit(int amount) {
        type(AMOUNT_INPUT, String.valueOf(amount));
        click(SUBMIT_BUTTON);
        return waitVisible(MESSAGE).getText();
    }
}
