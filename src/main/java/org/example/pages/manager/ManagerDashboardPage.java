package org.example.pages.manager;

import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ManagerDashboardPage extends BasePage {

    private static final By ADD_CUSTOMER_TAB = By.cssSelector("[ng-click='addCust()']");
    private static final By OPEN_ACCOUNT_TAB = By.cssSelector("[ng-click='openAccount()']");
    private static final By CUSTOMERS_TAB = By.cssSelector("[ng-click='showCust()']");

    public ManagerDashboardPage(WebDriver driver) {
        super(driver);
    }

    public AddCustomerPage goToAddCustomer() {
        click(ADD_CUSTOMER_TAB);
        return new AddCustomerPage(driver);
    }

    public OpenAccountPage goToOpenAccount() {
        click(OPEN_ACCOUNT_TAB);
        return new OpenAccountPage(driver);
    }

    public CustomersPage goToCustomers() {
        click(CUSTOMERS_TAB);
        return new CustomersPage(driver);
    }
}
