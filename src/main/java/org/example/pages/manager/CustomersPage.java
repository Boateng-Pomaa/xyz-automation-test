package org.example.pages.manager;

import java.util.List;
import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CustomersPage extends BasePage {

    private static final By SEARCH_INPUT = By.cssSelector("input[ng-model='searchCustomer']");
    private static final By TABLE_ROWS = By.cssSelector("table tbody tr");

    public CustomersPage(WebDriver driver) {
        super(driver);
    }

    public CustomersPage search(String text) {
        type(SEARCH_INPUT, text);
        return this;
    }

    public int getVisibleRowCount() {
        waitVisible(SEARCH_INPUT);
        return driver.findElements(TABLE_ROWS).size();
    }

    private By rowByName(String firstName, String lastName) {
        return By.xpath(String.format(
                "//tr[td[1][normalize-space()='%s'] and td[2][normalize-space()='%s']]",
                firstName, lastName));
    }

    public boolean isCustomerPresent(String firstName, String lastName) {
        waitVisible(SEARCH_INPUT);
        return !driver.findElements(rowByName(firstName, lastName)).isEmpty();
    }

    public void deleteCustomer(String firstName, String lastName) {
        By row = rowByName(firstName, lastName);
        WebElement deleteButton = waitVisible(row).findElement(By.xpath(".//button[text()='Delete']"));
        deleteButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(row));
    }

    public List<WebElement> getRows() {
        return driver.findElements(TABLE_ROWS);
    }

    /** Text of the "Account Number" column for the given customer's row. */
    public String getAccountsForCustomer(String firstName, String lastName) {
        WebElement row = waitVisible(rowByName(firstName, lastName));
        return row.findElements(By.tagName("td")).get(3).getText();
    }
}
