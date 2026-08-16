package org.example.manager;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.base.BaseTest;
import org.example.pages.manager.CustomersPage;
import org.example.pages.manager.ManagerDashboardPage;
import org.example.pages.manager.OpenAccountPage;
import org.junit.jupiter.api.Test;

@Epic("XYZ Bank")
@Feature("Manager - Account Management")
class OpenAccountTest extends BaseTest {

    @Test
    @Story("Open account")
    @Severity(SeverityLevel.CRITICAL)
    @Description("A manager can open a new account for an existing customer, and the account is listed against that customer.")
    void openAccount_forExistingCustomer_showsSuccessAlert_andListsNewAccount() {
        ManagerDashboardPage dashboard = homePage.goToManagerLogin();
        OpenAccountPage openAccountPage = dashboard.goToOpenAccount();

        String alertText = openAccountPage.openAccount("Harry Potter", "Pound");
        assertTrue(alertText.startsWith("Account created successfully with account Number :"),
                "Unexpected alert text: " + alertText);
        String accountNumber = alertText.replaceAll("\\D+", "");

        CustomersPage customersPage = dashboard.goToCustomers();
        customersPage.search("Potter");
        String accounts = customersPage.getAccountsForCustomer("Harry", "Potter");
        assertTrue(accounts.contains(accountNumber),
                "New account " + accountNumber + " not listed for customer: " + accounts);
    }
}
