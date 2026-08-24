package org.example.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.base.BaseTest;
import org.example.pages.customer.AccountPage;
import org.example.pages.customer.TransactionsPage;
import org.junit.jupiter.api.Test;

@Epic("XYZ Bank")
@Feature("Customer - Transaction History")
class TransactionHistoryTest extends BaseTest {

    @Test
    @Story("View transaction history")
    @Severity(SeverityLevel.NORMAL)
    @Description("A customer can view their transaction history, and it offers no edit or delete controls.")
    void transactionHistory_isViewableAndOffersNoEditOrDeleteControls() {
        AccountPage accountPage = openAccountForNewCustomer();
        accountPage.goToDeposit().deposit(10);

        TransactionsPage transactionsPage = accountPage.goToTransactions();

        assertTrue(transactionsPage.getTransactionCount() > 0,
                "Expected seeded transaction history to be non-empty");
        assertFalse(transactionsPage.hasMutationControls(),
                "Transaction history should not expose edit/delete controls");
    }

    @Test
    @Story("View transaction history")
    @Severity(SeverityLevel.NORMAL)
    @Description("A new deposit is reflected as a new record in the transaction history.")
    void deposit_addsNewRecordToTransactionHistory() {
        AccountPage accountPage = openAccountForNewCustomer();

        int countBefore = accountPage.goToTransactions().getTransactionCount();

        accountPage.goToDeposit().deposit(25);

        int countAfter = accountPage.goToTransactions().getTransactionCount();
        assertEquals(countBefore + 1, countAfter);
    }
}
