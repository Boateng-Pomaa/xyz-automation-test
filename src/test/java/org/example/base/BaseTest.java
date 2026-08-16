package org.example.base;

import org.example.pages.HomePage;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeEach
    void setUp() {
        driver = SharedDriver.get();
        homePage = new HomePage(driver).reset();
    }
}
