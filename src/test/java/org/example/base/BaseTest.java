package org.example.base;

import org.example.pages.HomePage;
import org.example.pages.customer.AccountPage;
import org.example.pages.customer.CustomerLoginPage;
import org.example.pages.manager.ManagerDashboardPage;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeEach
    void setUp() {
        driver = SharedDriver.get();

        homePage = new HomePage(driver).reset();
    }

    /**
     * Creates a brand-new customer and account and logs in as that customer,
     * so tests assert against state they seeded themselves rather than the
     * shared, mutable data of pre-existing demo customers (whose balance and
     * transaction history drift as other people exercise the same public site).
     */
    protected AccountPage openAccountForNewCustomer() {
        String firstName = "Auto";
        String lastName = "Test" + System.nanoTime();
        String fullName = firstName + " " + lastName;

        ManagerDashboardPage dashboard = homePage.goToManagerLogin();
        String addAlert = dashboard.goToAddCustomer().addCustomer(firstName, lastName, "00000");
        if (!addAlert.startsWith("Customer added successfully")) {
            throw new IllegalStateException("Failed to seed test customer: " + addAlert);
        }

        String openAlert = dashboard.goToOpenAccount().openAccount(fullName, "Dollar");
        if (!openAlert.startsWith("Account created successfully with account Number :")) {
            throw new IllegalStateException("Failed to seed test account: " + openAlert);
        }
        String accountNumber = openAlert.replaceAll("\\D+", "");

        homePage.reset();
        CustomerLoginPage loginPage = homePage.goToCustomerLogin();
        AccountPage accountPage = loginPage.loginAs(fullName);
        return accountPage.selectAccount(accountNumber);
    }
}
