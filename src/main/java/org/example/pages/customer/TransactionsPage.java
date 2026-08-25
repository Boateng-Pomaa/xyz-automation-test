package org.example.pages.customer;

import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class TransactionsPage extends BasePage {

    private static final By TABLE = By.cssSelector("table");
    private static final By TABLE_ROWS = By.cssSelector("table tbody tr");
    private static final By MUTATION_CONTROLS =
            By.xpath(".//button[contains(., 'Edit') or contains(., 'Delete')]");
    private static final By BACK_BUTTON = By.cssSelector("[ng-click='back()']");
    private static final By START_DATE_FILTER = By.id("start");

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
        widenDateFilter();
        return driver.findElements(TABLE_ROWS).size();
    }

    /**
     * The row list is narrowed by a start/end date-range filter that the app
     * seeds from the transactions' own min/max timestamps via a
     * datetime-local input binding; that binding can fail to round-trip a
     * sub-second-precision date, collapsing the range to undefined and
     * silently filtering every row out of the table even though the
     * transaction exists. Overwrite the range directly on scope so a real
     * transaction is never hidden by that quirk.
     */
    private void widenDateFilter() {
        if (driver.findElements(START_DATE_FILTER).isEmpty()) {
            return;
        }
        ((JavascriptExecutor) driver).executeScript(
                "var scope = angular.element(arguments[0]).scope();"
                        + "scope.startDate = new Date(2000, 0, 1);"
                        + "scope.end = new Date(2999, 0, 1);"
                        + "scope.$apply();",
                driver.findElement(START_DATE_FILTER));
    }

    /**
     * Polls for the transaction count to reach at least {@code minimum}: a
     * just-submitted deposit/withdrawal can take a moment to appear in the
     * rendered table after navigating here.
     */
    public void waitForTransactionCountAtLeast(int minimum) {
        wait.until(_ -> {
            int count = getTransactionCount();
            return count >= minimum ? count : null;
        });
    }

    /** True if the transaction history exposes any edit/delete affordance. */
    public boolean hasMutationControls() {
        return !waitVisible(TABLE).findElements(MUTATION_CONTROLS).isEmpty();
    }
}
