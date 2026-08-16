package org.example.pages.customer;

import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransactionsPage extends BasePage {

    private static final By TABLE = By.cssSelector("table");
    private static final By TABLE_ROWS = By.cssSelector("table tbody tr");
    private static final By MUTATION_CONTROLS =
            By.xpath(".//button[contains(., 'Edit') or contains(., 'Delete')]");

    public TransactionsPage(WebDriver driver) {
        super(driver);
    }

    public int getTransactionCount() {
        waitVisible(TABLE);
        return driver.findElements(TABLE_ROWS).size();
    }

    /** True if the transaction history exposes any edit/delete affordance. */
    public boolean hasMutationControls() {
        return !waitVisible(TABLE).findElements(MUTATION_CONTROLS).isEmpty();
    }
}
