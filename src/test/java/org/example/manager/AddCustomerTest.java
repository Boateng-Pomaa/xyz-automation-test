package org.example.manager;

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
class AddCustomerTest extends BaseTest {

    @Test
    @Story("Add customer")
    @Severity(SeverityLevel.CRITICAL)
    @Description("A manager can add a new customer, and the customer immediately appears in the customer list.")
    void addCustomer_showsSuccessAlert_andAppearsInCustomerList() {
        String uniqueLastName = "Tester" + System.currentTimeMillis();

        ManagerDashboardPage dashboard = homePage.goToManagerLogin();
        AddCustomerPage addCustomerPage = dashboard.goToAddCustomer();

        String alertText = addCustomerPage.addCustomer("Ada", uniqueLastName, "12345");
        assertTrue(alertText.startsWith("Customer added successfully"),
                "Unexpected alert text: " + alertText);

        CustomersPage customersPage = dashboard.goToCustomers();
        customersPage.search(uniqueLastName);
        assertTrue(customersPage.isCustomerPresent("Ada", uniqueLastName),
                "Newly added customer was not found in the customer list");
    }
}
