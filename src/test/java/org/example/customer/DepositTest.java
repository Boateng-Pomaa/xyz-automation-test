package org.example.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.base.BaseTest;
import org.example.pages.customer.AccountPage;
import org.example.pages.customer.CustomerLoginPage;
import org.example.pages.customer.DepositPage;
import org.junit.jupiter.api.Test;

@Epic("XYZ Bank")
@Feature("Customer - Transactions")
class DepositTest extends BaseTest {

    @Test
    @Story("Deposit")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Depositing a positive amount increases the account balance by that amount.")
    void deposit_positiveAmount_increasesBalance() {
        CustomerLoginPage loginPage = homePage.goToCustomerLogin();
        AccountPage accountPage = loginPage.loginAs("Harry Potter");

        int balanceBefore = accountPage.getBalance();

        DepositPage depositPage = accountPage.goToDeposit();
        String message = depositPage.deposit(100);

        assertEquals("Deposit Successful", message);
        assertEquals(balanceBefore + 100, accountPage.getBalance());
    }
}
