package org.example.manager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.base.BaseTest;
import org.example.pages.manager.AddCustomerPage;
import org.example.pages.manager.CustomersPage;
import org.example.pages.manager.ManagerDashboardPage;
import org.junit.jupiter.api.Test;

@Epic("XYZ Bank")
@Feature("Manager - Customer Management")
class DeleteCustomerTest extends BaseTest {

    @Test
    @Story("Delete customer")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Deleting a customer removes them from the customer list, revoking further access.")
    void deleteCustomer_removesCustomerFromList_andRevokesAccess() {
        String uniqueLastName = "Deletable" + System.currentTimeMillis();

        ManagerDashboardPage dashboard = homePage.goToManagerLogin();
        AddCustomerPage addCustomerPage = dashboard.goToAddCustomer();
        addCustomerPage.addCustomer("Doomed", uniqueLastName, "54321");

        CustomersPage customersPage = dashboard.goToCustomers();
        customersPage.search(uniqueLastName);
        assertTrue(customersPage.isCustomerPresent("Doomed", uniqueLastName),
                "Customer should exist before deletion");

        customersPage.deleteCustomer("Doomed", uniqueLastName);

        customersPage.search(uniqueLastName);
        assertFalse(customersPage.isCustomerPresent("Doomed", uniqueLastName),
                "Customer should no longer be listed after deletion");
    }
}
