package org.example.pages.customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class AccountPage extends BasePage {

    private static final By ACCOUNT_SELECT = By.id("accountSelect");
    private static final By ACCOUNT_SUMMARY = By.cssSelector("div.center");
    private static final By TRANSACTIONS_TAB = By.cssSelector("[ng-click='transactions()']");
    private static final By DEPOSIT_TAB = By.cssSelector("[ng-click='deposit()']");
    private static final By WITHDRAW_TAB = By.cssSelector("[ng-click='withdrawl()']");
    private static final By LOGOUT_BUTTON = By.cssSelector("[ng-click='byebye()']");

    private static final Pattern SUMMARY_PATTERN = Pattern.compile(
            "Account Number\\s*:\\s*(\\d+).*?Balance\\s*:\\s*(\\d+).*?Currency\\s*:\\s*(\\w+)",
            Pattern.DOTALL);

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage selectAccount(String accountNumber) {
        new Select(waitVisible(ACCOUNT_SELECT)).selectByVisibleText(accountNumber);
        return this;
    }

    private Matcher summaryMatcher() {
        String summary = waitVisible(ACCOUNT_SUMMARY).getText();
        Matcher matcher = SUMMARY_PATTERN.matcher(summary);
        if (!matcher.find()) {
            throw new IllegalStateException("Unable to parse account summary: " + summary);
        }
        return matcher;
    }

    public String getAccountNumber() {
        return summaryMatcher().group(1);
    }

    public int getBalance() {
        return Integer.parseInt(summaryMatcher().group(2));
    }

    public String getCurrency() {
        return summaryMatcher().group(3);
    }

    public DepositPage goToDeposit() {
        click(DEPOSIT_TAB);
        return new DepositPage(driver);
    }

    public WithdrawPage goToWithdraw() {
        click(WITHDRAW_TAB);
        return new WithdrawPage(driver);
    }

    public TransactionsPage goToTransactions() {
        click(TRANSACTIONS_TAB);
        return new TransactionsPage(driver);
    }

    public void logout() {
        click(LOGOUT_BUTTON);
    }
}
