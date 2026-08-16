package org.example.pages.customer;

import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CustomerLoginPage extends BasePage {

    private static final By CUSTOMER_SELECT = By.id("userSelect");
    private static final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");

    public CustomerLoginPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage loginAs(String customerFullName) {
        new Select(waitVisible(CUSTOMER_SELECT)).selectByVisibleText(customerFullName);
        click(LOGIN_BUTTON);
        return new AccountPage(driver);
    }
}
