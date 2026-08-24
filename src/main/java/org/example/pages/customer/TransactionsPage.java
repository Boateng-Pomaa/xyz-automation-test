package org.example.pages.customer;

import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransactionsPage extends BasePage {

    private static final By TABLE = By.cssSelector("table");
    private static final By TABLE_ROWS = By.cssSelector("table tbody tr");
    private static final By MUTATION_CONTROLS =
            By.xpath(".//button[contains(., 'Edit') or contains(., 'Delete')]");
    private static final By BACK_BUTTON = By.cssSelector("[ng-click='back()']");

    public TransactionsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * The transaction list is a separate view with no Deposit/Withdraw tabs of
     * its own - callers must go back to the account view before using those.
     */
    public AccountPage back() {
        click(BACK_BUTTON);
        return new AccountPage(driver);
    }

    public int getTransactionCount() {
        waitVisible(TABLE);
        return driver.findElements(TABLE_ROWS).size();
    }

    /**
     * Polls for the transaction count to reach at least {@code minimum}: a
     * just-submitted deposit/withdrawal can take a moment to appear in the
     * rendered table after navigating here.
     */
    public int waitForTransactionCountAtLeast(int minimum) {
        return wait.until(d -> {
            int count = getTransactionCount();
            return count >= minimum ? count : null;
        });
    }

    /** True if the transaction history exposes any edit/delete affordance. */
    public boolean hasMutationControls() {
        return !waitVisible(TABLE).findElements(MUTATION_CONTROLS).isEmpty();
    }
}
