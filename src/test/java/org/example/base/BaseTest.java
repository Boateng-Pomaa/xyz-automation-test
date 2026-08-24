package org.example.base;

import org.example.pages.HomePage;
import org.example.pages.customer.AccountPage;
import org.example.pages.customer.CustomerLoginPage;
import org.example.pages.customer.TransactionsPage;
import org.example.pages.manager.ManagerDashboardPage;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.TimeoutException;
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

        homePage.reset();
        CustomerLoginPage loginPage = homePage.goToCustomerLogin();
        AccountPage accountPage = loginPage.loginAs(fullName);
        accountPage.getBalance();
        return accountPage;
    }

    /**
     * Deposits into the account and waits for the transaction history to
     * reflect it, retrying the deposit if it doesn't: the demo site's balance
     * update is reliable, but it occasionally drops the corresponding
     * transaction-history entry regardless of how long the UI is given to
     * settle, so simply waiting longer doesn't help.
     */
    protected TransactionsPage depositUntilTransactionRecorded(AccountPage accountPage, int amount, int countBefore) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            accountPage.goToDeposit().deposit(amount);
            TransactionsPage transactionsPage = accountPage.goToTransactions();
            try {
                transactionsPage.waitForTransactionCountAtLeast(countBefore + 1);
                return transactionsPage;
            } catch (TimeoutException e) {
                if (attempt == 3) {
                    throw e;
                }
                accountPage = transactionsPage.back();
            }
        }
        throw new IllegalStateException("unreachable");
    }
}
