package org.example.pages.manager;

import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddCustomerPage extends BasePage {

    private static final By FIRST_NAME_INPUT = By.cssSelector("input[ng-model='fName']");
    private static final By LAST_NAME_INPUT = By.cssSelector("input[ng-model='lName']");
    private static final By POST_CODE_INPUT = By.cssSelector("input[ng-model='postCd']");
    private static final By SUBMIT_BUTTON =
            By.cssSelector("form[ng-submit='addCustomer()'] button[type='submit']");

    public AddCustomerPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Submits the add-customer form and returns the resulting native alert's text
     * (without dismissing navigation) so callers can assert success/failure.
     */
    public String addCustomer(String firstName, String lastName, String postCode) {
        type(FIRST_NAME_INPUT, firstName);
        type(LAST_NAME_INPUT, lastName);
        type(POST_CODE_INPUT, postCode);
        click(SUBMIT_BUTTON);
        return acceptAlertAndGetText();
    }
}
