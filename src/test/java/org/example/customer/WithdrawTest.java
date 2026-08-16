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
import org.example.pages.customer.WithdrawPage;
import org.junit.jupiter.api.Test;

@Epic("XYZ Bank")
@Feature("Customer - Transactions")
class WithdrawTest extends BaseTest {

    @Test
    @Story("Withdraw")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Withdrawing a valid amount decreases the account balance by that amount.")
    void withdraw_validAmount_decreasesBalance() {
        CustomerLoginPage loginPage = homePage.goToCustomerLogin();
        AccountPage accountPage = loginPage.loginAs("Ron Weasly");

        int balanceBefore = accountPage.getBalance();

        WithdrawPage withdrawPage = accountPage.goToWithdraw();
        String message = withdrawPage.withdraw(50);

        assertEquals("Transaction successful", message);
        assertEquals(balanceBefore - 50, accountPage.getBalance());
    }

    @Test
    @Story("Withdraw")
    @Severity(SeverityLevel.NORMAL)
    @Description("Withdrawing more than the available balance is rejected and the balance stays unchanged.")
    void withdraw_amountExceedingBalance_isRejected_andBalanceUnchanged() {
        CustomerLoginPage loginPage = homePage.goToCustomerLogin();
        AccountPage accountPage = loginPage.loginAs("Albus Dumbledore");

        int balanceBefore = accountPage.getBalance();

        WithdrawPage withdrawPage = accountPage.goToWithdraw();
        String message = withdrawPage.withdraw(balanceBefore + 1_000_000);

        assertEquals("Transaction Failed. You can not withdraw amount more than the balance.",
                message);
        assertEquals(balanceBefore, accountPage.getBalance());
    }
}
