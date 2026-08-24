package org.example.pages;

import java.time.Duration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Clicking can race an Angular re-render of the target element between the
     * clickable-wait resolving and the click itself, so a stale reference is
     * retried against a freshly located element rather than failing the test.
     */
    protected void click(By locator) {
        StaleElementReferenceException lastFailure = null;
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                waitClickable(locator).click();
                return;
            } catch (StaleElementReferenceException e) {
                lastFailure = e;
            }
        }
        throw lastFailure;
    }

    protected void type(By locator, String text) {
        WebElement element = waitVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String acceptAlertAndGetText() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }
}
